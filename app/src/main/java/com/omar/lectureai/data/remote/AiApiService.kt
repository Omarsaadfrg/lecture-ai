package com.omar.lectureai.data.remote

import com.omar.lectureai.data.model.dto.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface AiApiService {

    // 🔐 AUTH
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): LoginResponseDto

    @POST("auth/register")
    suspend fun register(
        @Body request: LoginRequestDto
    ): LoginResponseDto


    // 🔥 UPLOAD (Async Job)
    @Multipart
    @POST("ai/transcribe")
    suspend fun uploadAudio(
        @Part audio: MultipartBody.Part
    ): UploadResponse


    // 🔥 POLLING JOB STATUS
    @GET("ai/job/{id}")
    suspend fun getJobStatus(
        @Path("id") id: String
    ): JobStatusDto
}