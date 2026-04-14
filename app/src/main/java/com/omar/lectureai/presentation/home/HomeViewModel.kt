package com.omar.lectureai.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.lectureai.domain.usecase.ProcessLectureUseCase
import com.omar.lectureai.presentation.navigation.LectureResultStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToResult: Boolean = false,
)

class HomeViewModel(
    private val processLecture: ProcessLectureUseCase,
    private val lectureResultStore: LectureResultStore,
    private val app: Application,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onUploadClicked() {
        val path = File(app.cacheDir, "demo_upload.m4a").absolutePath
        process(path)
    }

    fun onRecordClicked() {
        val path = File(app.cacheDir, "demo_record.m4a").absolutePath
        process(path)
    }

    private fun process(audioFilePath: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            processLecture(audioFilePath)
                .onSuccess { lecture ->
                    lectureResultStore.set(lecture)
                    _uiState.update {
                        it.copy(isLoading = false, navigateToResult = true)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Something went wrong",
                        )
                    }
                }
        }
    }

    fun consumeNavigateToResult() {
        _uiState.update { it.copy(navigateToResult = false) }
    }
}
