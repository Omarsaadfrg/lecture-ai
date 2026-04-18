package com.omar.lectureai.domain.service

interface AiService {
    suspend fun transcribe(audioFilePath: String): Result<String>
    suspend fun summarize(transcript: String): Result<String>
    suspend fun generateQuestions(summary: String): Result<List<String>>
}
