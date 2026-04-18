package com.omar.lectureai.presentation.result

sealed class LectureUiState {
    object Idle : LectureUiState()
    object Loading : LectureUiState()

    data class Success(
        val transcript: String,
        val summary: String,
        val questions: List<String>
    ) : LectureUiState()

    data class Error(val message: String) : LectureUiState()
}