// File: app/src/main/java/com/example/smartdisplay/data/MediaTrack.kt
package com.example.smartdisplay.data

data class MediaTrack(
    val id: String = "1",
    val title: String = "Sin reproducción activa",
    val artist: String = "Toca Play para reanudar",
    val album: String = "Smart Display Ambient",
    val coverUrl: String = "",
    val durationSeconds: Int = 180,
    val currentPositionSeconds: Int = 0,
    val sourceApp: String = "Spotify"
)

// File: app/src/main/java/com/example/smartdisplay/data/PomodoroState.kt
enum class PomodoroMode(val durationMinutes: Int, val label: String) {
    WORK(25, "Enfoque"),
    SHORT_BREAK(5, "Pausa Corta"),
    LONG_BREAK(15, "Descanso Largo")
}

data class PomodoroState(
    val mode: PomodoroMode = PomodoroMode.WORK,
    val timeLeftSeconds: Int = 25 * 60,
    val isRunning: Boolean = false,
    val completedCycles: Int = 0
)