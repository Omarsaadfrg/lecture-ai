package com.omar.lectureai.data.model.dto
import com.omar.lectureai.presentation.result.TranscriptBlock

data class JobResultDto(
    val transcript: String? = null,

    // ✅ النص الكامل بدون timestamps
    val fullText: String? = null,

    // ✅ البلوكات المتقسمة للعرض
    val blocks: List<TranscriptBlock> = emptyList(),

    val summary: String? = null,

    val questions: List<String>? = null
)