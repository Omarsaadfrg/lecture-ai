package com.omar.lectureai.data.service

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseStorageHelper {

    private val storage = FirebaseStorage.getInstance()

    suspend fun uploadAudio(uri: Uri): String {
        return try {
            val fileName = "audio/${UUID.randomUUID()}.mp3"
            val ref = storage.reference.child(fileName)

            ref.putFile(uri).await()

            // get download url
            ref.downloadUrl.await().toString()

        } catch (e: Exception) {
            throw Exception("Upload failed: ${e.message}")
        }
    }
}