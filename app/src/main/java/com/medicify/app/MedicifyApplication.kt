package com.medicify.app

import android.app.Application
import com.medicify.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MedicifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MedicifyApplication)
            androidLogger()
            modules(appModule)
        }
    }
}