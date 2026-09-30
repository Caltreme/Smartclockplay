package com.example.smartdisplay.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartdisplay.models.MediaState
import com.example.smartdisplay.models.MediaTrack
import com.example.smartdisplay.models.PomodoroMode
import com.example.smartdisplay.models.PomodoroState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    
    private val defaultPlaylist = listOf(
        MediaTrack("1", "Midnight City", "M83", "Hurry Up, We're Dreaming", "https://images.unsplash.com/photo-1614613535308-eb5fbd3d2c17?w=500&q=80", "Spotify", 243),
        MediaTrack("2", "Starboy", "The Weeknd", "Starboy", "https://images.unsplash.com/photo-1493225457124-a1a2a5f5f9af?w=500&q=80", "Spotify", 230),
        MediaTrack("3", "Get Lucky", "Daft Punk", "Random Access Memories", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=500&q=80", "Apple Music", 248)
    )

    private val _mediaState = MutableStateFlow(
        MediaState(
            currentTrack = defaultPlaylist[0],
            playlist = defaultPlaylist
        )
    )
    val mediaState: StateFlow<MediaState> = _mediaState.asStateFlow()

    private val _pomodoroState = MutableStateFlow(PomodoroState())
    val pomodoroState: StateFlow<PomodoroState> = _pomodoroState.asStateFlow()

    private var pomodoroJob: Job? = null
    private var mediaJob: Job? = null

    fun togglePlayPause() {
        val isPlaying = !_mediaState.value.isPlaying
        _mediaState.update { it.copy(isPlaying = isPlaying) }
        
        mediaJob?.cancel()
        if (isPlaying) {
            mediaJob = viewModelScope.launch {
                while (true) {
                    delay(1000)
                    _mediaState.update { state ->
                        val newPos = state.position + 1
                        if (newPos >= (state.currentTrack?.duration ?: 0)) {
                            nextTrack()
                            state 
                        } else {
                            state.copy(position = newPos)
                        }
                    }
                }
            }
        }
    }

    fun nextTrack() {
        _mediaState.update { state ->
            val currentIndex = state.playlist.indexOf(state.currentTrack)
            val nextIndex = (currentIndex + 1) % state.playlist.size
            state.copy(currentTrack = state.playlist[nextIndex], position = 0)
        }
    }

    fun previousTrack() {
        _mediaState.update { state ->
            val currentIndex = state.playlist.indexOf(state.currentTrack)
            val prevIndex = if (currentIndex - 1 < 0) state.playlist.size - 1 else currentIndex - 1
            state.copy(currentTrack = state.playlist[prevIndex], position = 0)
        }
    }

    fun seekMedia(position: Int) {
        _mediaState.update { it.copy(position = position) }
    }

    fun setAppPlayed(app: String) {
        _mediaState.update { it.copy(lastAppPlayed = app) }
    }

    // Pomodoro Logic
    fun setPomodoroMode(mode: PomodoroMode) {
        pomodoroJob?.cancel()
        _pomodoroState.update { 
            it.copy(mode = mode, timeLeft = mode.minutes * 60, isRunning = false)
        }
    }

    fun togglePomodoro() {
        val isRunning = !_pomodoroState.value.isRunning
        _pomodoroState.update { it.copy(isRunning = isRunning) }
        
        pomodoroJob?.cancel()
        if (isRunning) {
            pomodoroJob = viewModelScope.launch {
                while (_pomodoroState.value.timeLeft > 0) {
                    delay(1000)
                    _pomodoroState.update { it.copy(timeLeft = it.timeLeft - 1) }
                }
                _pomodoroState.update { 
                    it.copy(
                        isRunning = false, 
                        completedCycles = if (it.mode == PomodoroMode.WORK) it.completedCycles + 1 else it.completedCycles 
                    )
                }
            }
        }
    }

    fun resetPomodoro() {
        pomodoroJob?.cancel()
        _pomodoroState.update { 
            it.copy(timeLeft = it.mode.minutes * 60, isRunning = false)
        }
    }
}
