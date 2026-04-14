package com.omar.lectureai.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val state: String = "",
)

class HomeViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onUploadClicked() {
        TODO("Not yet implemented")
    }

    fun onRecordClicked() {
        TODO("Not yet implemented")
    }

    fun consumeNavigateToResult() {
        TODO("Not yet implemented")
    }
}
