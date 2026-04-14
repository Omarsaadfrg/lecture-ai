package com.omar.lectureai.presentation.result

import androidx.lifecycle.ViewModel
import com.omar.lectureai.domain.model.Lecture
import com.omar.lectureai.presentation.navigation.LectureResultStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultViewModel(
    lectureResultStore: LectureResultStore,
) : ViewModel() {

    private val _lecture = MutableStateFlow(lectureResultStore.take())
    val lecture: StateFlow<Lecture?> = _lecture.asStateFlow()
}
