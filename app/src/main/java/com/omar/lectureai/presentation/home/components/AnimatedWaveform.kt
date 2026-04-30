package com.omar.lectureai.presentation.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun AnimatedWaveform() {

    val barCount = 40

    val infiniteTransition = rememberInfiniteTransition(
        label = "waveform"
    )

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        ),
        label = "phase"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val accent = HomeColors.Accent
    val accentDim = HomeColors.AccentDim

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {

        val spacing = size.width / barCount
        val barWidth = spacing * 0.55f
        val centerY = size.height / 2f
        val maxHeight = size.height * 0.85f
        val glowPadding = 4.dp.toPx()

        for (i in 0 until barCount) {

            val x = i * spacing + spacing / 2f

            val wave1 = sin(phase + i * 0.4f)
            val wave2 = sin(phase * 1.3f + i * 0.25f) * 0.5f
            val wave3 = sin(phase * 0.7f + i * 0.6f) * 0.3f

            val normalized =
                ((wave1 + wave2 + wave3) / 1.8f + 1f) / 2f

            val height =
                (normalized * maxHeight)
                    .coerceAtLeast(6.dp.toPx())

            val color = lerp(
                start = accentDim.copy(alpha = 0.5f),
                stop = accent.copy(alpha = glowAlpha),
                fraction = normalized.coerceIn(0f, 1f)
            )

            drawLine(
                color = color.copy(alpha = 0.25f),
                start = Offset(
                    x,
                    centerY - height / 2f - glowPadding
                ),
                end = Offset(
                    x,
                    centerY + height / 2f + glowPadding
                ),
                strokeWidth = barWidth + glowPadding,
                cap = StrokeCap.Round
            )

            drawLine(
                color = color,
                start = Offset(
                    x,
                    centerY - height / 2f
                ),
                end = Offset(
                    x,
                    centerY + height / 2f
                ),
                strokeWidth = barWidth,
                cap = StrokeCap.Round
            )
        }
    }
}