package com.omar.lectureai.di

import com.omar.lectureai.data.repository.LectureRepositoryImpl
import com.omar.lectureai.data.service.AiServiceImpl
import com.omar.lectureai.domain.repository.LectureRepository
import com.omar.lectureai.domain.service.AiService
import com.omar.lectureai.domain.usecase.GenerateQuestionsUseCase
import com.omar.lectureai.domain.usecase.ProcessLectureUseCase
import com.omar.lectureai.domain.usecase.SummarizeLectureUseCase
import com.omar.lectureai.domain.usecase.TranscribeLectureUseCase
import com.omar.lectureai.presentation.home.HomeViewModel
import com.omar.lectureai.presentation.result.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AiService> { AiServiceImpl() }
    single<LectureRepository> { LectureRepositoryImpl(get()) }
    factory { TranscribeLectureUseCase(get()) }
    factory { SummarizeLectureUseCase(get()) }
    factory { GenerateQuestionsUseCase(get()) }
    factory { ProcessLectureUseCase(get()) }
    viewModel { HomeViewModel() }
    viewModel { ResultViewModel() }
}
