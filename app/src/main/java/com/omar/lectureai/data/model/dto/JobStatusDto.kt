package com.omar.lectureai.data.model.dto

data class JobStatusDto(
    val status: String,
    val result: JobResultDto? = null,
    val error: String? = null
)