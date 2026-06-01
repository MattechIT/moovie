package com.example.moovie

import android.app.Application
import com.example.moovie.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@MoovieApp)
            modules(appModule)
        }
    }
}
