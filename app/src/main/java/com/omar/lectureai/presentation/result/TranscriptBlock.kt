package com.omar.lectureai.presentation.result

data class TranscriptBlock(
    val id: Int,
    val start: Double,
    val end: Double,
    val text: String
)