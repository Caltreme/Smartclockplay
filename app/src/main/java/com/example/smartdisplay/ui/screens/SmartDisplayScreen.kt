package com.example.smartdisplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartdisplay.models.MediaState
import com.example.smartdisplay.models.PomodoroState
import com.example.smartdisplay.viewmodels.MainViewModel

@Composable
fun SmartDisplayScreen(
    mediaState: MediaState,
    pomodoroState: PomodoroState,
    viewModel: MainViewModel
) {
    var selectedTab by remember { mutableStateOf("media") }

    Row(modifier = Modifier.fillMaxSize().background(Color(0xFF09090B))) {
        // Sidebar Navigation
        Column(
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()
                .background(Color(0xFF18181B))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedTab == "media") Color(0xFF10B981).copy(alpha = 0.2f) else Color.Transparent)
                    .clickable { selectedTab = "media" },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.LibraryMusic, contentDescription = "Media", tint = if (selectedTab == "media") Color(0xFF10B981) else Color.Gray)
            }
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedTab == "productivity") Color(0xFF6366F1).copy(alpha = 0.2f) else Color.Transparent)
                    .clickable { selectedTab = "productivity" },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Timer, contentDescription = "Productivity", tint = if (selectedTab == "productivity") Color(0xFF6366F1) else Color.Gray)
            }
        }

        // Main Content Area
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            when (selectedTab) {
                "media" -> MediaModule(mediaState, viewModel)
                "productivity" -> ProductivityModule(pomodoroState, viewModel)
            }
        }
    }
}
