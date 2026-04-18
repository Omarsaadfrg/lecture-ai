package com.omar.lectureai.domain.usecase

import com.omar.lectureai.domain.repository.LectureRepository

class SummarizeLectureUseCase(
    private val repository: LectureRepository,
) {
    suspend operator fun invoke(transcript: String): Result<String> {
        TODO("Not yet implemented")
    }
}
