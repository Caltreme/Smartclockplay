package com.example.smartdisplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.smartdisplay.models.MediaState
import com.example.smartdisplay.viewmodels.MainViewModel

@Composable
fun MediaModule(
    mediaState: MediaState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val track = mediaState.currentTrack ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha=0.1f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(Icons.Default.DiscFull, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("REPRODUCTOR INTELIGENTE", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier
                    .background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha=0.1f), RoundedCornerShape(16.dp))
                    .padding(4.dp)
            ) {
                val apps = listOf("Spotify", "YouTube Music", "Apple Music")
                apps.forEach { app ->
                    val isSelected = mediaState.lastAppPlayed == app
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Color(0xFF10B981) else Color.Transparent)
                            .clickable { viewModel.setAppPlayed(app) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(app, color = if (isSelected) Color.White else Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Main Content Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Artwork Left
            Box(
                modifier = Modifier
                    .weight(0.45f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = track.coverUrl,
                    contentDescription = "Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(240.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(32.dp))
                )
            }

            // Controls Right
            Column(
                modifier = Modifier
                    .weight(0.55f)
                    .padding(start = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(track.title, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                Text("${track.artist} — ${track.album}", color = Color(0xFF10B981), fontSize = 18.sp, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(32.dp))

                // Scrubber
                Slider(
                    value = mediaState.position.toFloat(),
                    onValueChange = { viewModel.seekMedia(it.toInt()) },
                    valueRange = 0f..track.duration.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF10B981),
                        activeTrackColor = Color(0xFF10B981),
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(formatTime(mediaState.position), color = Color.Gray, fontSize = 14.sp)
                    Text(formatTime(track.duration), color = Color.Gray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Big Media Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.seekMedia((mediaState.position - 15).coerceAtLeast(0)) },
                        modifier = Modifier.size(56.dp).background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                    ) {
                        Icon(Icons.Default.Replay10, contentDescription = "-15s", tint = Color.White)
                    }
                    IconButton(
                        onClick = { viewModel.previousTrack() },
                        modifier = Modifier.size(64.dp).background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                    ) {
                        Icon(Icons.Default.SkipPrevious, contentDescription = "Prev", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    
                    // Main Play Button
                    IconButton(
                        onClick = { viewModel.togglePlayPause() },
                        modifier = Modifier
                            .size(80.dp)
                            .background(if (mediaState.isPlaying) Color(0xFFFBBF24) else Color(0xFF10B981), CircleShape)
                    ) {
                        Icon(
                            if (mediaState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = if (mediaState.isPlaying) Color.Black else Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = { viewModel.nextTrack() },
                        modifier = Modifier.size(64.dp).background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                    ) {
                        Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(32.dp))
                    }
                    IconButton(
                        onClick = { viewModel.seekMedia((mediaState.position + 15).coerceAtMost(track.duration)) },
                        modifier = Modifier.size(56.dp).background(Color(0xFF18181B), RoundedCornerShape(16.dp))
                    ) {
                        Icon(Icons.Default.Forward10, contentDescription = "+15s", tint = Color.White)
                    }
                }
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return String.format("%d:%02d", m, s)
}
