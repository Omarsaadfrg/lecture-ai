package com.omar.lectureai.presentation.processing

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omar.lectureai.data.remote.AiApiService
import com.omar.lectureai.presentation.result.ResultViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProcessingViewModel(
    private val api: AiApiService,
    private val resultViewModel: ResultViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProcessingUiState())
    val uiState: StateFlow<ProcessingUiState> = _uiState.asStateFlow()

    private var isProcessingStarted = false

    fun startProcessing(audioUri: Uri, context: Context) {

        if (isProcessingStarted) return
        isProcessingStarted = true

        viewModelScope.launch {

            try {

                // =========================
                // STEP 1 → UPLOAD
                // =========================

                _uiState.update {
                    it.copy(
                        currentStep = ProcessStep.UPLOADING,
                        progress = 0.1f
                    )
                }

                val file = uriToFile(audioUri, context)

                println("🔥 FILE PATH = ${file.absolutePath}")
                println("🔥 FILE SIZE = ${file.length()}")

                val mimeType =
                    context.contentResolver.getType(audioUri)
                        ?: "audio/*"

                val requestFile =
                    file.asRequestBody(
                        mimeType.toMediaTypeOrNull()
                    )

                val body = MultipartBody.Part.createFormData(
                    "audio",
                    file.name,
                    requestFile
                )

                println("🔥 START UPLOAD")

                val uploadResponse = api.uploadAudio(body)

                val jobId = uploadResponse.jobId

                println("🔥 JOB ID = $jobId")

                // =========================
                // STEP 2 → TRANSCRIBING
                // =========================

                _uiState.update {
                    it.copy(
                        currentStep = ProcessStep.TRANSCRIBING,
                        progress = 0.3f
                    )
                }

                var attempts = 0

                while (attempts < 120) {

                    delay(3000)

                    attempts++

                    println("🔥 POLLING ATTEMPT = $attempts")

                    val jobStatus = api.getJobStatus(jobId)

                    println("🔥 JOB STATUS = ${jobStatus.status}")
                    println("🔥 JOB RESULT = ${jobStatus.result}")

                    when (jobStatus.status) {

                        "waiting", "active" -> {

                            _uiState.update {
                                it.copy(
                                    currentStep = ProcessStep.TRANSCRIBING,
                                    progress = (
                                            it.progress + 0.01f
                                            ).coerceAtMost(0.85f)
                                )
                            }
                        }

                        "completed" -> {

                            val result = jobStatus.result

                            if (result == null) {
                                throw Exception("Result is null")
                            }

                            resultViewModel.setResult(result)

                            _uiState.update {
                                it.copy(
                                    currentStep = ProcessStep.GENERATING,
                                    progress = 0.95f
                                )
                            }

                            delay(800)

                            _uiState.update {
                                it.copy(
                                    isFinished = true,
                                    progress = 1f
                                )
                            }

                            break
                        }
                        "failed" -> {

                            throw Exception(
                                jobStatus.error
                                    ?: "Processing failed from server"
                            )
                        }

                        else -> {
                            println("⚠️ UNKNOWN STATUS = ${jobStatus.status}")
                        }
                    }
                }

                // =========================
                // TIMEOUT
                // =========================

                if (attempts >= 120) {
                    throw Exception("Timeout: Processing took too long")
                }

            } catch (e: Exception) {

                e.printStackTrace()

                println("❌ PROCESSING ERROR = ${e.message}")

                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "Processing failed"
                    )
                }
            }
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {

        val inputStream =
            context.contentResolver.openInputStream(uri)
                ?: throw Exception("Cannot open file")

        val file =
            File.createTempFile(
                "audio_",
                ".mp3",
                context.cacheDir
            )

        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }

        inputStream.close()

        return file
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}