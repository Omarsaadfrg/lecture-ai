package com.omar.lectureai.domain.repository

import com.omar.lectureai.domain.model.LectureResult

interface LectureRepository {
    suspend fun transcribeLecture(audioFilePath: String): Result<String>
    suspend fun summarizeLecture(transcript: String): Result<String>
    suspend fun generateQuestions(summary: String): Result<List<String>>
    suspend fun processLecture(audioFilePath: String): Result<LectureResult>
}
