package com.example.smartdisplay.models

data class MediaTrack(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val coverUrl: String,
    val sourceApp: String,
    val duration: Int // duración en segundos
)

data class MediaState(
    val isPlaying: Boolean = false,
    val currentTrack: MediaTrack? = null,
    val position: Int = 0,
    val volume: Float = 0.5f,
    val lastAppPlayed: String = "Spotify",
    val playlist: List<MediaTrack> = emptyList()
)
