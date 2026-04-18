package com.omar.lectureai.presentation.processing

// الخطوات بالترتيب
enum class ProcessStep(val label: String) {
    UPLOADING("Uploading"),
    TRANSCRIBING("Transcribing"),
    SUMMARIZING("Summarizing"),
    GENERATING("Generating")
}

// حالة الشاشة
data class ProcessingUiState(
    val currentStep: ProcessStep = ProcessStep.UPLOADING,
    val progress: Float = 0f,        // 0.0 → 1.0
    val etaSeconds: Int = 0,
    val isFinished: Boolean = false,
    val errorMessage: String? = null
)