package com.omar.lectureai.presentation.processing

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProcessingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProcessingUiState())
    val uiState: StateFlow<ProcessingUiState> = _uiState.asStateFlow()

    private var isProcessingStarted = false

    fun startProcessing(audioUri: Uri) {

        if (isProcessingStarted) return
        isProcessingStarted = true

        viewModelScope.launch {
            try {

                // 🔥 STEP 1: Uploading (REAL later)
                _uiState.update {
                    it.copy(
                        currentStep = ProcessStep.UPLOADING,
                        progress = 0.1f
                    )
                }

                // 🔥 مؤقتًا لحد ما نربط Node API
                delay(2000)

                _uiState.update {
                    it.copy(progress = 1f)
                }

                // باقي الخطوات (لسه fake)
                simulateStep(
                    step = ProcessStep.TRANSCRIBING,
                    durationMs = 4000L
                )

                simulateStep(
                    step = ProcessStep.SUMMARIZING,
                    durationMs = 3000L
                )

                simulateStep(
                    step = ProcessStep.GENERATING,
                    durationMs = 2000L
                )

                _uiState.update {
                    it.copy(
                        isFinished = true,
                        progress = 1f
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "Upload failed"
                    )
                }
            }
        }
    }

    private suspend fun simulateStep(step: ProcessStep, durationMs: Long) {
        val tickMs = 50L
        val ticks = durationMs / tickMs
        val etaStart = (durationMs / 1000).toInt()

        for (tick in 0..ticks) {
            val progress = tick.toFloat() / ticks.toFloat()
            val etaLeft = (etaStart * (1f - progress)).toInt()

            _uiState.update {
                it.copy(
                    currentStep = step,
                    progress = progress,
                    etaSeconds = etaLeft
                )
            }

            delay(tickMs)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}