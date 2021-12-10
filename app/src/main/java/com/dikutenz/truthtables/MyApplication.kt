package com.dikutenz.truthtables

import android.app.Application
import com.dikutenz.truthtables.di.appModule
import com.dikutenz.truthtables.di.repoModule
import com.dikutenz.truthtables.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(listOf(appModule, viewModelModule, repoModule))
        }
    }

}