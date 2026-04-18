package com.omar.lectureai.presentation.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*

// ──────────────────────────────────────────────
//  COLORS
// ──────────────────────────────────────────────
private object HomeColors {
    val Background    = Color(0xFF0A0A0F)
    val Surface       = Color(0xFF13131A)
    val SurfaceHigh   = Color(0xFF1C1C27)
    val Accent        = Color(0xFF00F5A0)
    val AccentDim     = Color(0xFF00C87A)
    val AccentGlow    = Color(0x4000F5A0)
    val TextPrimary   = Color(0xFFF0F0F5)
    val TextSecondary = Color(0xFF8A8A9A)
    val Border        = Color(0xFF2A2A38)
    val GridLine      = Color(0x0FFFFFFF)
}

// ──────────────────────────────────────────────
//  SCREEN
//  onUploadClick(Uri) — called after user picks file
//  onRecordClick(Uri) — called after recording stops
// ──────────────────────────────────────────────
@Composable
fun HomeScreen(
    onUploadClick: (Uri) -> Unit = {},
    onRecordClick: (Uri) -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onNavigateToResult: () -> Unit = {}
) {
    // File picker — accepts audio files
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onUploadClick(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.Background)
    ) {
        GridBackground()

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-60).dp)
                .size(340.dp)
                .blur(100.dp)
                .background(Brush.radialGradient(listOf(HomeColors.AccentGlow, Color.Transparent)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Spacer(modifier = Modifier.height(16.dp))
                HistoryButton(onClick = onHistoryClick)
            }

            Spacer(modifier = Modifier.weight(1f))

            LogoSection()

            Spacer(modifier = Modifier.height(40.dp))

            AnimatedWaveform()

            Spacer(modifier = Modifier.height(48.dp))

            // Upload — opens file picker
            UploadButton(onClick = { filePicker.launch("audio/*") })

            Spacer(modifier = Modifier.height(12.dp))

            // Record — TODO: hook to real recorder, using dummy URI for now
            RecordButton(onClick = {
                // Replace with real recorded file URI when you add recording logic
                onRecordClick(Uri.EMPTY)
            })

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Supports MP3, WAV, M4A  •  Up to 2 hours",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = HomeColors.TextSecondary,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.3.sp
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ──────────────────────────────────────────────
//  GRID BACKGROUND
// ──────────────────────────────────────────────
@Composable
private fun GridBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val cellSize = 52.dp.toPx()
        val cols = (size.width / cellSize).toInt() + 1
        val rows = (size.height / cellSize).toInt() + 1
        for (i in 0..cols) drawLine(HomeColors.GridLine, Offset(i * cellSize, 0f), Offset(i * cellSize, size.height), 1f)
        for (j in 0..rows) drawLine(HomeColors.GridLine, Offset(0f, j * cellSize), Offset(size.width, j * cellSize), 1f)
    }
}

// ──────────────────────────────────────────────
//  HISTORY BUTTON
// ──────────────────────────────────────────────
@Composable
private fun HistoryButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 20.dp, end = 4.dp)
            .size(46.dp)
            .clip(CircleShape)
            .background(HomeColors.SurfaceHigh)
            .border(1.dp, HomeColors.Border, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Rounded.History, "History", tint = HomeColors.TextSecondary, modifier = Modifier.size(22.dp))
    }
}

// ──────────────────────────────────────────────
//  LOGO
// ──────────────────────────────────────────────
@Composable
private fun LogoSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text("Lecture", style = TextStyle(fontSize = 42.sp, fontWeight = FontWeight.ExtraBold,
                color = HomeColors.TextPrimary, letterSpacing = (-1).sp))
            Text("AI", style = TextStyle(fontSize = 42.sp, fontWeight = FontWeight.ExtraBold,
                color = HomeColors.Accent, letterSpacing = (-1).sp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(modifier = Modifier.width(120.dp).height(2.dp).clip(RoundedCornerShape(1.dp))
            .background(Brush.horizontalGradient(listOf(Color.Transparent, HomeColors.Accent, Color.Transparent))))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Turn lectures into notes instantly", style = TextStyle(fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold, color = HomeColors.TextPrimary, textAlign = TextAlign.Center))
        Spacer(modifier = Modifier.height(10.dp))
        Text("Upload or record a lecture, AI will transcribe,\nsummarize, and generate questions",
            style = TextStyle(fontSize = 14.sp, color = HomeColors.TextSecondary,
                textAlign = TextAlign.Center, lineHeight = 22.sp))
    }
}

// ──────────────────────────────────────────────
//  ANIMATED WAVEFORM
// ──────────────────────────────────────────────
@Composable
private fun AnimatedWaveform() {
    val barCount = 40
    val inf = rememberInfiniteTransition(label = "waveform")
    val phase by inf.animateFloat(0f, (2 * PI).toFloat(),
        infiniteRepeatable(tween(2000, easing = LinearEasing)), label = "phase")
    val glowAlpha by inf.animateFloat(0.4f, 0.9f,
        infiniteRepeatable(tween(1200, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "glow")
    val accent = HomeColors.Accent
    val accentDim = HomeColors.AccentDim

    Canvas(modifier = Modifier.fillMaxWidth().height(90.dp)) {
        val spacing = size.width / barCount
        val barWidth = spacing * 0.55f
        val centerY = size.height / 2f
        val maxH = size.height * 0.85f
        for (i in 0 until barCount) {
            val x = i * spacing + spacing / 2f
            val w1 = sin(phase + i * 0.4f)
            val w2 = sin(phase * 1.3f + i * 0.25f) * 0.5f
            val w3 = sin(phase * 0.7f + i * 0.6f) * 0.3f
            val norm = ((w1 + w2 + w3) / 1.8f + 1f) / 2f
            val h = (norm * maxH).coerceAtLeast(6.dp.toPx())
            val color = lerp(accentDim.copy(alpha = 0.5f), accent.copy(alpha = glowAlpha), norm.coerceIn(0f,1f))
            drawLine(color.copy(alpha=0.25f), Offset(x, centerY-h/2f-4.dp.toPx()),
                Offset(x, centerY+h/2f+4.dp.toPx()), barWidth+6.dp.toPx(), cap=StrokeCap.Round)
            drawLine(color, Offset(x, centerY-h/2f), Offset(x, centerY+h/2f), barWidth, cap=StrokeCap.Round)
        }
    }
}

// ──────────────────────────────────────────────
//  UPLOAD BUTTON
// ──────────────────────────────────────────────
@Composable
private fun UploadButton(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.96f else 1f,
        spring(stiffness = Spring.StiffnessMedium), label = "us")
    Box(
        modifier = Modifier.fillMaxWidth().graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.horizontalGradient(listOf(HomeColors.Accent, HomeColors.AccentDim)))
            .clickable { pressed = true; onClick() }
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Upload, null, tint = HomeColors.Background, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(10.dp))
            Text("Upload Lecture", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold,
                color = HomeColors.Background))
        }
    }
    LaunchedEffect(pressed) { if (pressed) { delay(150); pressed = false } }
}

// ──────────────────────────────────────────────
//  RECORD BUTTON
// ──────────────────────────────────────────────
@Composable
private fun RecordButton(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.96f else 1f,
        spring(stiffness = Spring.StiffnessMedium), label = "rs")
    Box(
        modifier = Modifier.fillMaxWidth().graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(28.dp))
            .background(HomeColors.SurfaceHigh)
            .border(1.dp, HomeColors.Border, RoundedCornerShape(28.dp))
            .clickable { pressed = true; onClick() }
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Mic, null, tint = HomeColors.TextPrimary, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(10.dp))
            Text("Record Lecture", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold,
                color = HomeColors.TextPrimary))
        }
    }
    LaunchedEffect(pressed) { if (pressed) { delay(150); pressed = false } }
}