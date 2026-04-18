package com.omar.lectureai.data.service

import com.omar.lectureai.domain.service.AiService

class AiServiceImpl(
) : AiService {

    override suspend fun transcribe(audioFilePath: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun summarize(transcript: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun generateQuestions(summary: String): Result<List<String>> {
        TODO("Not yet implemented")
    }
}
