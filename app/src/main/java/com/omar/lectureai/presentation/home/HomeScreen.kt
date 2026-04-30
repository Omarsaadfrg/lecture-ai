package com.omar.lectureai.presentation.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omar.lectureai.presentation.home.components.AnimatedWaveform
import com.omar.lectureai.presentation.home.components.GridBackground
import com.omar.lectureai.presentation.home.components.HistoryButton
import com.omar.lectureai.presentation.home.components.HomeColors
import com.omar.lectureai.presentation.home.components.LogoSection
import com.omar.lectureai.presentation.home.components.RecordButton
import com.omar.lectureai.presentation.home.components.SelectedAudioCard
import com.omar.lectureai.presentation.home.components.UploadButton

@Composable
fun HomeScreen(
    onUploadClick: (Uri) -> Unit = {},
    onRecordClick: (Uri) -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onNavigateToResult: () -> Unit = {}
) {

    var selectedUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val filePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri != null) {
                selectedUri = uri
            }
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
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            HomeColors.AccentGlow,
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {

                HistoryButton(
                    onClick = onHistoryClick
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            LogoSection()

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            AnimatedWaveform()

            Spacer(
                modifier = Modifier.height(48.dp)
            )

            UploadButton(
                onClick = {
                    filePicker.launch("audio/*")
                }
            )

            selectedUri?.let { uri ->

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                SelectedAudioCard(

                    onUpload = {
                        onUploadClick(uri)
                    },

                    onCancel = {
                        selectedUri = null
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            RecordButton(
                onClick = {

                    // TODO:
                    // Replace with real recording URI
                    onRecordClick(Uri.EMPTY)
                }
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Supports MP3, WAV, M4A  •  Up to 2 hours",

                style = TextStyle(
                    fontSize = 12.sp,
                    color = HomeColors.TextSecondary,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.3.sp
                )
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}