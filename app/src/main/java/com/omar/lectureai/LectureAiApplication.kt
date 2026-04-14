package com.omar.lectureai

import android.app.Application
import com.omar.lectureai.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LectureAiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LectureAiApplication)
            modules(appModule)
        }
    }
}
