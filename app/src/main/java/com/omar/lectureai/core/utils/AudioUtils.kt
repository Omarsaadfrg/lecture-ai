package com.omar.lectureai.core.utils

object AudioUtils {

    fun guessMimeType(fileName: String): String {
        val lower = fileName.lowercase()
        return when {
            lower.endsWith(".mp3") -> "audio/mpeg"
            lower.endsWith(".m4a") || lower.endsWith(".aac") -> "audio/mp4"
            lower.endsWith(".wav") -> "audio/wav"
            lower.endsWith(".ogg") -> "audio/ogg"
            else -> "audio/*"
        }
    }

    fun isSupportedExtension(fileName: String): Boolean {
        val lower = fileName.lowercase()
        return lower.endsWith(".mp3") ||
            lower.endsWith(".m4a") ||
            lower.endsWith(".aac") ||
            lower.endsWith(".wav") ||
            lower.endsWith(".ogg")
    }
}
