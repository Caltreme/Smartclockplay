package com.example.smartdisplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.smartdisplay.ui.components.MediaModuleCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var isPlaying by remember { mutableStateOf(false) }

                    MediaModuleCompose(
                        isPlaying = isPlaying,
                        onPlayPauseToggle = { isPlaying = !isPlaying },
                        onNext = { /* Siguiente canción */ },
                        onPrev = { /* Canción anterior */ }
                    )
                }
            }
        }
    }
}