package com.omar.lectureai.data.repository

import com.omar.lectureai.data.model.LectureDto
import com.omar.lectureai.data.remote.AiApiService
import com.omar.lectureai.domain.model.Lecture
import com.omar.lectureai.domain.repository.LectureRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class LectureRepositoryImpl(
    private val api: AiApiService,
    private val ioDispatcher: CoroutineDispatcher,
) : LectureRepository {

    override suspend fun processLecture(audioFilePath: String): Result<Lecture> =
        withContext(ioDispatcher) {
            runCatching {
                val file = File(audioFilePath)
                val mediaType = "audio/*".toMediaTypeOrNull()
                val body = file.asRequestBody(mediaType)
                val part = MultipartBody.Part.createFormData("audio", file.name, body)
                api.uploadAudio(part).toDomain()
            }.recoverCatching { throwable ->
                placeholderLecture(audioFilePath, throwable.message)
            }
        }

    private fun LectureDto.toDomain(): Lecture = Lecture(
        id = id.orEmpty().ifEmpty { "remote" },
        transcript = transcript.orEmpty(),
        summary = summary.orEmpty(),
    )

    private fun placeholderLecture(audioFilePath: String, errorDetail: String?): Lecture =
        Lecture(
            id = "local-placeholder",
            transcript = "Placeholder transcript (API not reachable). File: $audioFilePath",
            summary = "Placeholder summary. ${errorDetail?.let { "($it)" } ?: ""}".trim(),
        )
}
