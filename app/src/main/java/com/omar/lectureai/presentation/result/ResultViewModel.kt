package com.omar.lectureai.presentation.result

import androidx.lifecycle.ViewModel
import com.omar.lectureai.data.model.dto.JobResultDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultViewModel : ViewModel() {

    private val _result =
        MutableStateFlow<JobResultDto?>(null)

    val result: StateFlow<JobResultDto?> =
        _result.asStateFlow()

    fun setResult(result: JobResultDto) {
        _result.value = result
    }

    fun clearResult() {
        _result.value = null
    }
}