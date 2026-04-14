package com.omar.lectureai.presentation.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResultScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = koinViewModel(),
) {
    val lecture by viewModel.lecture.collectAsState()

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

        val lec = lecture
        if (lec == null) {
            Text(
                text = "No lecture data. Go back and process audio first.",
                style = MaterialTheme.typography.bodyLarge,
            )
        } else {
            Text(
                text = "Transcript",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = lec.transcript,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "Summary",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = lec.summary,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Button(onClick = onBack, modifier = Modifier.padding(top = 8.dp)) {
            Text("Back")
        }
    }
}
