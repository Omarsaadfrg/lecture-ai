package com.omar.lectureai.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    context: Context
) : Interceptor {

    private val prefs =
        context.getSharedPreferences(
            "auth",
            Context.MODE_PRIVATE
        )

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val token =
            prefs.getString("token", null)

        val requestBuilder =
            chain.request()
                .newBuilder()

        if (!token.isNullOrEmpty()) {

            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(
            requestBuilder.build()
        )
    }
}