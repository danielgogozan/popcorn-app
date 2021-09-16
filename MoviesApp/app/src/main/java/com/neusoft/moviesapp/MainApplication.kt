package com.neusoft.moviesapp

import android.app.Application
import com.neusoft.moviesapp.utils.homeScreenViewModelModule
import com.neusoft.moviesapp.utils.movieRepositoryModule
import com.neusoft.moviesapp.utils.retrofitModule
import com.neusoft.moviesapp.utils.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(homeScreenViewModelModule, retrofitModule, movieRepositoryModule, roomModule))
        }
    }
}