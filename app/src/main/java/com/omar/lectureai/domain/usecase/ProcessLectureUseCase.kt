package com.omar.lectureai.domain.usecase

import com.omar.lectureai.domain.model.Lecture
import com.omar.lectureai.domain.repository.LectureRepository

class ProcessLectureUseCase(
    private val repository: LectureRepository,
) {
    suspend operator fun invoke(audioFilePath: String): Result<Lecture> =
        repository.processLecture(audioFilePath)
}
