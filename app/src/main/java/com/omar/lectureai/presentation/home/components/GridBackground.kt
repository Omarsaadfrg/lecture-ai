package com.omar.lectureai.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun GridBackground(
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {

        val cellSize = 52.dp.toPx()

        val columns =
            (size.width / cellSize).toInt() + 1

        val rows =
            (size.height / cellSize).toInt() + 1

        for (column in 0..columns) {

            val x = column * cellSize

            drawLine(
                color = HomeColors.GridLine,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
        }

        for (row in 0..rows) {

            val y = row * cellSize

            drawLine(
                color = HomeColors.GridLine,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }
    }
}