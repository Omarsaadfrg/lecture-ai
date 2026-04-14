package com.omar.lectureai.data.model

import com.google.gson.annotations.SerializedName

data class LectureDto(
    @SerializedName("id") val id: String?,
    @SerializedName("transcript") val transcript: String?,
    @SerializedName("summary") val summary: String?,
)
