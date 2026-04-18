package com.omar.lectureai.domain.usecase

import com.omar.lectureai.domain.repository.LectureRepository

class TranscribeLectureUseCase(
    private val repository: LectureRepository,
) {
    suspend operator fun invoke(audioFilePath: String): Result<String> {
        TODO("Not yet implemented")
    }
}
