package com.omar.lectureai.data.repository

import com.omar.lectureai.domain.model.LectureResult
import com.omar.lectureai.domain.repository.LectureRepository
import com.omar.lectureai.domain.service.AiService

class LectureRepositoryImpl(
    private val aiService: AiService,
) : LectureRepository {

    override suspend fun transcribeLecture(audioFilePath: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun summarizeLecture(transcript: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun generateQuestions(summary: String): Result<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun processLecture(audioFilePath: String): Result<LectureResult> {
        TODO("Not yet implemented")
    }
}
