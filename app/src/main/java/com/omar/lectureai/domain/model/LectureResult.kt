package com.omar.lectureai.domain.model

data class LectureResult(
    val transcript: String = "",
    val summary: String = "",
    val questions: List<String> = emptyList(),
)
