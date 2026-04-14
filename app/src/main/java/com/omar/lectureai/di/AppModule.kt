package com.omar.lectureai.di

import android.app.Application
import com.omar.lectureai.core.constants.Constants
import com.omar.lectureai.data.remote.AiApiService
import com.omar.lectureai.data.repository.LectureRepositoryImpl
import com.omar.lectureai.domain.repository.LectureRepository
import com.omar.lectureai.domain.usecase.ProcessLectureUseCase
import com.omar.lectureai.presentation.home.HomeViewModel
import com.omar.lectureai.presentation.navigation.LectureResultStore
import com.omar.lectureai.presentation.result.ResultViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    single<Application> { androidContext().applicationContext as Application }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), Constants.BASE_URL) }
    single<AiApiService> { get<Retrofit>().create(AiApiService::class.java) }
    single<LectureRepository> { LectureRepositoryImpl(get(), get()) }
    single { LectureResultStore() }
    factory { ProcessLectureUseCase(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ResultViewModel(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
