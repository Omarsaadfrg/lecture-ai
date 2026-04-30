package com.omar.lectureai.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AudioFile
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectedAudioCard(
    onUpload: () -> Unit,
    onCancel: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(HomeColors.SurfaceHigh)
            .border(
                width = 1.dp,
                color = HomeColors.Border,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Rounded.AudioFile,
                contentDescription = "Selected Audio",
                tint = HomeColors.Accent
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Audio selected",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = HomeColors.TextPrimary
                )
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(HomeColors.Accent)
                    .clickable {
                        onUpload()
                    }
                    .padding(vertical = 14.dp),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Start Processing",
                    color = HomeColors.Background,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(HomeColors.Surface)
                    .border(
                        width = 1.dp,
                        color = HomeColors.Border,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .clickable {
                        onCancel()
                    }
                    .padding(vertical = 14.dp),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Cancel",
                    color = HomeColors.TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}