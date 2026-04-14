package com.omar.lectureai.data.remote

import com.omar.lectureai.data.model.dto.QuestionsDto
import com.omar.lectureai.data.model.dto.QuestionsRequestDto
import com.omar.lectureai.data.model.dto.SummarizeRequestDto
import com.omar.lectureai.data.model.dto.SummaryDto
import com.omar.lectureai.data.model.dto.TranscriptionDto

interface AiApiService {
    suspend fun transcribeAudio(audioPath: String): TranscriptionDto
    suspend fun summarize(request: SummarizeRequestDto): SummaryDto
    suspend fun generateQuestions(request: QuestionsRequestDto): QuestionsDto
}
