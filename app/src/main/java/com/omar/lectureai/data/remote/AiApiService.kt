package com.omar.lectureai.data.remote

import com.omar.lectureai.data.model.LectureDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AiApiService {

    @Multipart
    @POST("v1/lectures/upload-audio")
    suspend fun uploadAudio(
        @Part audio: MultipartBody.Part,
    ): LectureDto
}
