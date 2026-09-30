package com.example.smartdisplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.smartdisplay.ui.screens.SmartDisplayScreen
import com.example.smartdisplay.ui.theme.SmartDisplayTheme
import com.example.smartdisplay.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartDisplayTheme {
                val mediaState by viewModel.mediaState.collectAsState()
                val pomodoroState by viewModel.pomodoroState.collectAsState()
                
                SmartDisplayScreen(
                    mediaState = mediaState,
                    pomodoroState = pomodoroState,
                    viewModel = viewModel
                )
            }
        }
    }
}
