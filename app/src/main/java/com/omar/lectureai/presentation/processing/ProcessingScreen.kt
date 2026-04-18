package com.omar.lectureai.presentation.processing

import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.*

// ─────────────────────────────────────────────
//  COLORS
// ─────────────────────────────────────────────
private object PC {
    val Background  = Color(0xFF0A0A0F)
    val SurfaceHigh = Color(0xFF1C1C27)
    val Accent      = Color(0xFF00F5A0)
    val AccentDim   = Color(0xFF00C87A)
    val AccentGlow  = Color(0x4000F5A0)
    val AccentFaint = Color(0x1500F5A0)
    val TextPri     = Color(0xFFF0F0F5)
    val TextSec     = Color(0xFF8A8A9A)
    val Border      = Color(0xFF2A2A38)
    val StepDone    = Color(0xFF00F5A0)
    val StepIdle    = Color(0xFF2A2A3A)
}

// ─────────────────────────────────────────────
//  SCREEN  — connected to ViewModel
// ─────────────────────────────────────────────
@Composable
fun ProcessingScreen(
    audioUri: Uri,                        // passed from HomeScreen
    onFinished: () -> Unit,               // navigate to ResultScreen
    viewModel: ProcessingViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Start processing once when the screen appears
    LaunchedEffect(audioUri) {
        viewModel.startProcessing(audioUri)
    }

    // Navigate away when done
    LaunchedEffect(state.isFinished) {
        if (state.isFinished) onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PC.Background)
    ) {
        // Background glow
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 80.dp)
                .size(380.dp)
                .blur(120.dp)
                .background(Brush.radialGradient(listOf(PC.AccentGlow, Color.Transparent)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.8f))

            OrbitalAnimation(progress = state.progress)

            Spacer(modifier = Modifier.height(44.dp))

            // Title
            Text(
                text = state.currentStep.label,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PC.TextPri,
                    letterSpacing = (-0.5).sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            val subtitle = when (state.currentStep) {
                ProcessStep.UPLOADING    -> "Uploading your audio file..."
                ProcessStep.TRANSCRIBING -> "Converting speech to text..."
                ProcessStep.SUMMARIZING  -> "Generating smart summary..."
                ProcessStep.GENERATING   -> "Creating practice questions..."
            }
            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 15.sp,
                    color = PC.TextSec,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProgressSection(progress = state.progress, etaSeconds = state.etaSeconds)

            Spacer(modifier = Modifier.height(32.dp))

            StepsRow(currentStep = state.currentStep)

            Spacer(modifier = Modifier.weight(1f))

            // Error snackbar
            state.errorMessage?.let { msg ->
                Snackbar(
                    modifier = Modifier.padding(bottom = 24.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("Dismiss", color = PC.Accent)
                        }
                    }
                ) { Text(msg) }
            }
        }
    }
}

// ─────────────────────────────────────────────
//  ORBITAL ANIMATION
// ─────────────────────────────────────────────
@Composable
private fun OrbitalAnimation(progress: Float) {
    val inf = rememberInfiniteTransition(label = "orbital")

    val angle1 by inf.animateFloat(0f, 360f,
        infiniteRepeatable(tween(4000, easing = LinearEasing)), label = "r1")
    val angle2 by inf.animateFloat(360f, 0f,
        infiniteRepeatable(tween(6500, easing = LinearEasing)), label = "r2")
    val angle3 by inf.animateFloat(0f, 360f,
        infiniteRepeatable(tween(9000, easing = LinearEasing)), label = "r3")
    val pulse by inf.animateFloat(0.85f, 1f,
        infiniteRepeatable(tween(1000, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "pulse")
    val twinkle by inf.animateFloat(0.5f, 1f,
        infiniteRepeatable(tween(700, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "twinkle")

    val accent    = PC.Accent
    val accentDim = PC.AccentDim
    val accentGlow = PC.AccentGlow

    Box(modifier = Modifier.size(260.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.size(200.dp).blur(40.dp)
                .background(Brush.radialGradient(listOf(PC.AccentFaint, Color.Transparent)))
        )

        Canvas(modifier = Modifier.size(260.dp)) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            fun ring(radius: Float, angleDeg: Float, dotR: Float, alpha: Float) {
                drawCircle(
                    color = accent.copy(alpha = 0.16f), radius = radius,
                    center = Offset(cx, cy),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(1.2f)
                )
                val px = cx + radius * cos(Math.toRadians(angleDeg.toDouble())).toFloat()
                val py = cy + radius * sin(Math.toRadians(angleDeg.toDouble())).toFloat()
                drawCircle(color = accentGlow, radius = dotR * 2f, center = Offset(px, py))
                drawCircle(color = accent.copy(alpha = alpha * twinkle), radius = dotR, center = Offset(px, py))
            }

            ring(115.dp.toPx(), angle1, 6.dp.toPx(), 1f)
            ring(82.dp.toPx(),  angle2, 5.dp.toPx(), 0.8f)
            ring(52.dp.toPx(),  angle3, 4.dp.toPx(), 0.6f)
        }

        // Centre percentage circle
        Box(
            modifier = Modifier
                .size((72 * pulse).dp).clip(CircleShape)
                .background(Brush.radialGradient(listOf(accentDim.copy(0.9f), accent.copy(0.6f)))),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(54.dp).clip(CircleShape).background(Color(0xFF0D1F18)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = PC.Accent)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
//  PROGRESS BAR + ETA
// ─────────────────────────────────────────────
@Composable
private fun ProgressSection(progress: Float, etaSeconds: Int) {
    val animated by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "prog"
    )
    val inf = rememberInfiniteTransition(label = "shimmer")
    val shimmerX by inf.animateFloat(-1f, 2f,
        infiniteRepeatable(tween(1400, easing = LinearEasing)), label = "sx")

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${(animated * 100).toInt()}%",
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PC.Accent))
            if (etaSeconds > 0)
                Text("ETA: ${etaSeconds}s", style = TextStyle(fontSize = 13.sp, color = PC.TextSec))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(6.dp)
                .clip(RoundedCornerShape(3.dp)).background(PC.SurfaceHigh)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(animated.coerceIn(0f, 1f)).fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(Brush.horizontalGradient(listOf(PC.AccentDim, PC.Accent)))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val s = shimmerX * size.width - size.width * 0.4f
                    drawRect(brush = Brush.horizontalGradient(
                        listOf(Color.Transparent, Color.White.copy(0.25f), Color.Transparent),
                        startX = s, endX = s + size.width * 0.4f
                    ))
                }
            }
            // glowing lead dot
            Box(
                modifier = Modifier.fillMaxWidth(animated.coerceIn(0f, 1f)).fillMaxHeight(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.White))
            }
        }
    }
}

// ─────────────────────────────────────────────
//  STEPS ROW
// ─────────────────────────────────────────────
@Composable
private fun StepsRow(currentStep: ProcessStep) {
    val steps = ProcessStep.values()
    val currentIndex = steps.indexOf(currentStep)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, step ->
            val isDone   = index < currentIndex
            val isActive = index == currentIndex

            StepDot(label = step.label, isDone = isDone, isActive = isActive)

            if (index < steps.lastIndex) {
                Box(
                    modifier = Modifier.weight(1f).padding(top = 10.dp).height(1.5.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .background(if (isDone) PC.Accent.copy(0.6f) else PC.Border)
                )
            }
        }
    }
}

@Composable
private fun StepDot(label: String, isDone: Boolean, isActive: Boolean) {
    val inf = rememberInfiniteTransition(label = "dot_$label")
    val ripple by inf.animateFloat(1f, 1.6f,
        infiniteRepeatable(tween(900, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "ripple_$label"
    )
    val dotColor by animateColorAsState(
        targetValue = when { isDone -> PC.StepDone; isActive -> PC.Accent; else -> PC.StepIdle },
        animationSpec = tween(400), label = "dc_$label"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
        Box(contentAlignment = Alignment.Center) {
            if (isActive) {
                Box(modifier = Modifier.size((20 * ripple).dp).clip(CircleShape)
                    .background(PC.Accent.copy(0.2f)))
            }
            Box(
                modifier = Modifier.size(18.dp).clip(CircleShape).background(dotColor)
                    .then(if (!isDone && !isActive) Modifier.border(1.5.dp, PC.Border, CircleShape) else Modifier),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) Icon(Icons.Rounded.Check, null, tint = PC.Background, modifier = Modifier.size(11.dp))
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                color = when { isDone -> PC.Accent; isActive -> PC.TextPri; else -> PC.TextSec },
                textAlign = TextAlign.Center
            )
        )
    }
}