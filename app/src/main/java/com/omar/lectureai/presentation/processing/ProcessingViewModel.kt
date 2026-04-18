package com.omar.lectureai.presentation.processing

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProcessingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProcessingUiState())
    val uiState: StateFlow<ProcessingUiState> = _uiState.asStateFlow()

    // ─────────────────────────────────────────
    //  Entry point — call this from the Screen
    //  passing the URI the user picked/recorded
    // ─────────────────────────────────────────
    fun startProcessing(audioUri: Uri) {
        viewModelScope.launch {
            try {
                // ── Step 1: Uploading ──────────────────
                simulateStep(
                    step     = ProcessStep.UPLOADING,
                    durationMs = 3000L
                )

                // ── Step 2: Transcribing ───────────────
                simulateStep(
                    step     = ProcessStep.TRANSCRIBING,
                    durationMs = 4000L
                )

                // ── Step 3: Summarizing ────────────────
                simulateStep(
                    step     = ProcessStep.SUMMARIZING,
                    durationMs = 3000L
                )

                // ── Step 4: Generating Questions ───────
                simulateStep(
                    step     = ProcessStep.GENERATING,
                    durationMs = 2000L
                )

                // ── Done → trigger navigation ──────────
                _uiState.update { it.copy(isFinished = true, progress = 1f) }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Something went wrong") }
            }
        }
    }

    // ─────────────────────────────────────────
    //  Simulates smooth progress for one step
    //  Replace the body with your real API call
    // ─────────────────────────────────────────
    private suspend fun simulateStep(step: ProcessStep, durationMs: Long) {
        val tickMs  = 50L
        val ticks   = durationMs / tickMs
        val etaStart = (durationMs / 1000).toInt()

        for (tick in 0..ticks) {
            val progress  = tick.toFloat() / ticks.toFloat()
            val etaLeft   = (etaStart * (1f - progress)).toInt()

            _uiState.update {
                it.copy(
                    currentStep = step,
                    progress    = progress,
                    etaSeconds  = etaLeft
                )
            }
            delay(tickMs)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}