package com.omar.lectureai.di

import com.omar.lectureai.core.constants.Constants
import com.omar.lectureai.data.remote.AiApiService
import com.omar.lectureai.data.repository.AuthRepositoryImpl
import com.omar.lectureai.presentation.auth.LoginViewModel
import com.omar.lectureai.presentation.home.HomeViewModel
import com.omar.lectureai.presentation.processing.ProcessingViewModel
import com.omar.lectureai.presentation.result.ResultViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.omar.lectureai.data.remote.AuthInterceptor

val appModule = module {

    // =========================
    // OKHTTP
    // =========================

    single {

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()

            .connectTimeout(
                60,
                TimeUnit.SECONDS
            )

            .readTimeout(
                5,
                TimeUnit.MINUTES
            )

            .writeTimeout(
                5,
                TimeUnit.MINUTES
            )

            .addInterceptor(
                AuthInterceptor(get())
            )

            .addInterceptor { chain ->

                val request = chain.request()

                android.util.Log.d(
                    "API_URL",
                    "👉 ${request.url}"
                )

                chain.proceed(request)
            }

            .addInterceptor(logging)

            .build()
    }

    // =========================
    // RETROFIT
    // =========================

    single {

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // =========================
    // API SERVICE
    // =========================

    single<AiApiService> {
        get<Retrofit>().create(AiApiService::class.java)
    }

    // =========================
    // REPOSITORIES
    // =========================

    single {

        AuthRepositoryImpl(
            api = get(),
            context = get()
        )
    }

    // =========================
    // VIEWMODELS
    // =========================

    viewModel {

        LoginViewModel(
            repository = get()
        )
    }

    viewModel {

        ProcessingViewModel(
            api = get(),
            resultViewModel = get()
        )
    }

    viewModel {

        HomeViewModel()
    }

    single {
        ResultViewModel()
    }
}