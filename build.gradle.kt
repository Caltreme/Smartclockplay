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