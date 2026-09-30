package com.example.smartdisplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartdisplay.models.PomodoroMode
import com.example.smartdisplay.models.PomodoroState
import com.example.smartdisplay.viewmodels.MainViewModel

@Composable
fun ProductivityModule(
    pomodoroState: PomodoroState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Módulo de Productividad", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Mode Selector
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                PomodoroModeButton("Enfoque (25 min)", "Trabajo profundo", pomodoroState.mode == PomodoroMode.WORK) {
                    viewModel.setPomodoroMode(PomodoroMode.WORK)
                }
                Spacer(modifier = Modifier.height(16.dp))
                PomodoroModeButton("Pausa (5 min)", "Pausa activa", pomodoroState.mode == PomodoroMode.SHORT_BREAK) {
                    viewModel.setPomodoroMode(PomodoroMode.SHORT_BREAK)
                }
                Spacer(modifier = Modifier.height(16.dp))
                PomodoroModeButton("Descanso (15 min)", "Tras 4 ciclos", pomodoroState.mode == PomodoroMode.LONG_BREAK) {
                    viewModel.setPomodoroMode(PomodoroMode.LONG_BREAK)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier
                        .background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                        .border(1.dp, Color.White.copy(alpha=0.1f), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ciclos Completados:", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(pomodoroState.completedCycles.toString(), color = Color(0xFFFBBF24), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Right Timer & Large Buttons
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .border(12.dp, if (pomodoroState.mode == PomodoroMode.WORK) Color(0xFFE11D48) else Color(0xFF10B981), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = formatTime(pomodoroState.timeLeft),
                        color = Color.White,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { viewModel.togglePomodoro() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (pomodoroState.isRunning) Color(0xFFFBBF24) else Color(0xFFE11D48),
                            contentColor = if (pomodoroState.isRunning) Color.Black else Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.height(64.dp).padding(horizontal = 8.dp)
                    ) {
                        Icon(
                            if (pomodoroState.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, 
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            if (pomodoroState.isRunning) "Pausar" else "Iniciar Pomodoro",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(
                        onClick = { viewModel.resetPomodoro() },
                        modifier = Modifier.background(Color(0xFF18181B), RoundedCornerShape(20.dp)).size(64.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reiniciar", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PomodoroModeButton(title: String, subtitle: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Color(0xFFE11D48).copy(alpha = 0.15f) else Color(0xFF18181B))
            .border(2.dp, if (isSelected) Color(0xFFE11D48) else Color.White.copy(alpha=0.05f), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Text(title, color = if (isSelected) Color(0xFFFDA4AF) else Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(subtitle, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
    }
}
