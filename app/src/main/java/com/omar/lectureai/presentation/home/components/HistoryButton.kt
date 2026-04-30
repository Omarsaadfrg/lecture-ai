package com.omar.lectureai.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun HistoryButton(
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(
                top = 20.dp,
                end = 4.dp
            )
            .size(46.dp)
            .clip(CircleShape)
            .background(HomeColors.SurfaceHigh)
            .border(
                width = 1.dp,
                color = HomeColors.Border,
                shape = CircleShape
            )
            .clickable {
                onClick()
            },

        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Rounded.History,
            contentDescription = "History",
            tint = HomeColors.TextSecondary,
            modifier = Modifier.size(22.dp)
        )
    }
}