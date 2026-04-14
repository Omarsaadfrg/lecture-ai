package com.omar.lectureai.presentation.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResultScreen(
    audioPath: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(audioPath) {
        viewModel.processLecture(audioPath)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Result",
            style = MaterialTheme.typography.headlineMedium,
        )

        when (val state = uiState) {
            LectureUiState.Idle -> {
                Text(
                    text = "Ready to process lecture...",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            LectureUiState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = "Processing lecture...",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            is LectureUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            is LectureUiState.Success -> {
                Text(text = "Transcript", style = MaterialTheme.typography.titleMedium)
                Text(text = state.transcript, style = MaterialTheme.typography.bodyLarge)

                Text(text = "Summary", style = MaterialTheme.typography.titleMedium)
                Text(text = state.summary, style = MaterialTheme.typography.bodyLarge)

                Text(text = "Questions", style = MaterialTheme.typography.titleMedium)
                state.questions.forEachIndexed { index, question ->
                    Text(
                        text = "${index + 1}. $question",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }

        Button(onClick = onBack, modifier = Modifier.padding(top = 8.dp)) {
            Text("Back")
        }
    }
}
