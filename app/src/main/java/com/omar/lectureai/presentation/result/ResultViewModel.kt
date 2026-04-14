package com.omar.lectureai.presentation.result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow<LectureUiState>(LectureUiState.Idle)
    val uiState: StateFlow<LectureUiState> = _uiState.asStateFlow()

    fun processLecture(audioFilePath: String) {
        TODO("Not yet implemented")
    }
}
