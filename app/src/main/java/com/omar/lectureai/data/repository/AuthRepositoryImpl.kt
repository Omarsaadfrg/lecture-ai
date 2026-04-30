package com.omar.lectureai.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.omar.lectureai.data.model.dto.LoginRequestDto
import com.omar.lectureai.data.remote.AiApiService

class AuthRepositoryImpl(
    private val api: AiApiService,
    context: Context
) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            "auth",
            Context.MODE_PRIVATE
        )

    // =========================
    // LOGIN
    // =========================

    suspend fun login(
        email: String,
        password: String
    ): Result<String> {

        return try {

            Log.d(
                "LOGIN_DEBUG",
                "LOGIN CLICKED"
            )

            val response = api.login(
                LoginRequestDto(
                    email,
                    password
                )
            )

            Log.d(
                "LOGIN_DEBUG",
                "TOKEN = ${response.token}"
            )

            saveToken(response.token)

            Result.success(response.token)

        } catch (e: Exception) {

            Log.e(
                "LOGIN_ERROR",
                e.stackTraceToString()
            )

            Result.failure(e)
        }
    }

    // =========================
    // REGISTER
    // =========================

    suspend fun register(
        email: String,
        password: String
    ): Result<String> {

        return try {

            Log.d(
                "LOGIN_DEBUG",
                "REGISTER CLICKED"
            )

            val response = api.register(
                LoginRequestDto(
                    email,
                    password
                )
            )

            Log.d(
                "LOGIN_DEBUG",
                "REGISTER TOKEN = ${response.token}"
            )

            saveToken(response.token)

            Result.success(response.token)

        } catch (e: Exception) {

            Log.e(
                "REGISTER_ERROR",
                e.stackTraceToString()
            )

            Result.failure(e)
        }
    }

    // =========================
    // TOKEN
    // =========================

    fun saveToken(token: String) {

        prefs.edit()
            .putString("token", token)
            .apply()
    }

    fun getToken(): String? {

        return prefs.getString(
            "token",
            null
        )
    }

    fun logout() {

        prefs.edit()
            .clear()
            .apply()
    }
}