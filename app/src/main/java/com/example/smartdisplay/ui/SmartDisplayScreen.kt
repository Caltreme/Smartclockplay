// File: app/src/main/java/com/example/smartdisplay/ui/SmartDisplayScreen.kt
package com.example.smartdisplay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartdisplay.data.MediaTrack
import com.example.smartdisplay.ui.components.MediaModuleScreen
import com.example.smartdisplay.ui.components.ProductivityModuleScreen

@Composable
fun SmartDisplayScreen(
    currentTrack: MediaTrack,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121214))
            .padding(12.dp)
    ) {
        // Barra de Pestañas Superior
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFF1E1E22),
            contentColor = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Reproductor Inteligente", color = if (selectedTab == 0) Color(0xFF10B981) else Color.Gray) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Productividad & Pomodoro", color = if (selectedTab == 1) Color(0xFF10B981) else Color.Gray) }
            )
        }

        // Vista de Contenido Activo
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> MediaModuleScreen(
                    track = currentTrack,
                    isPlaying = isPlaying,
                    onPlayPauseToggle = onPlayPauseClick,
                    onNextTrack = onNextClick,
                    onPrevTrack = onPreviousClick
                )
                1 -> ProductivityModuleScreen()
            }
        }
    }
}