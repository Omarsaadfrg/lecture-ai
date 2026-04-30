package com.omar.lectureai.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogoSection() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {

            Text(
                text = "Lecture",
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HomeColors.TextPrimary,
                    letterSpacing = (-1).sp
                )
            )

            Text(
                text = "AI",
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = HomeColors.Accent,
                    letterSpacing = (-1).sp
                )
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Box(
            modifier = Modifier
                .width(120.dp)
                .height(2.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            HomeColors.Accent,
                            Color.Transparent
                        )
                    )
                )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Turn lectures into notes instantly",
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = HomeColors.TextPrimary,
                textAlign = TextAlign.Center
            )
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Upload or record a lecture, AI will transcribe,\nsummarize, and generate questions",
            style = TextStyle(
                fontSize = 14.sp,
                color = HomeColors.TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        )
    }
}