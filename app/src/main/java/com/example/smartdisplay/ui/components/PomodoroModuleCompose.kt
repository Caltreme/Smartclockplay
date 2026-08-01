package com.example.smartdisplay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PomodoroModuleCompose(
    secondsLeft: Int = 1500, // 25:00 por defecto
    isRunning: Boolean,
    onStartPause: () -> Unit,
    onReset: () -> Unit
) {
    val minutes = secondsLeft / 60
    val seconds = secondsLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Lado Izquierdo: Números Gigantes Legibles
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formattedTime,
                fontSize = 80.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFFFF5252)
            )
            Text(
                text = "ENFOQUE TRABAJO",
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray
            )
        }

        // Lado Derecho: Controles de Acción
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onStartPause,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) Color(0xFFFFC107) else Color(0xFFFF5252)
                ),
                modifier = Modifier.height(56.dp)
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isRunning) "Pausar" else "Iniciar",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = onReset,
                modifier = Modifier.size(56.dp).background(Color(0xFF222222), CircleShape)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reiniciar", tint = Color.White)
            }
        }
    }
}