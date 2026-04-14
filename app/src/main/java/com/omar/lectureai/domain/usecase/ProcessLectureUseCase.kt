package com.omar.lectureai.domain.usecase

import com.omar.lectureai.domain.model.LectureResult
import com.omar.lectureai.domain.repository.LectureRepository

class ProcessLectureUseCase(
    private val repository: LectureRepository,
) {
    suspend operator fun invoke(audioFilePath: String): Result<LectureResult> {
        TODO("Not yet implemented")
    }
}
