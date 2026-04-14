package com.omar.lectureai.domain.repository

import com.omar.lectureai.domain.model.Lecture

interface LectureRepository {
    suspend fun processLecture(audioFilePath: String): Result<Lecture>
}
