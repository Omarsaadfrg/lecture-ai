package com.omar.lectureai.domain.usecase

import com.omar.lectureai.domain.repository.LectureRepository

class GenerateQuestionsUseCase(
    private val repository: LectureRepository,
) {
    suspend operator fun invoke(summary: String): Result<List<String>> {
        TODO("Not yet implemented")
    }
}
