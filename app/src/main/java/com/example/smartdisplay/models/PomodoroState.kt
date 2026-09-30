package com.example.smartdisplay.models

enum class PomodoroMode(val minutes: Int) {
    WORK(25),
    SHORT_BREAK(5),
    LONG_BREAK(15)
}

data class PomodoroState(
    val mode: PomodoroMode = PomodoroMode.WORK,
    val timeLeft: Int = 25 * 60,
    val isRunning: Boolean = false,
    val completedCycles: Int = 0
)
