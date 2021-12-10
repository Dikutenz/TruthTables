package com.dikutenz.truthtables.di

import android.app.Application
import com.dikutenz.truthtables.model.AppDatabase
import org.koin.dsl.module

private fun provideDatabase(application: Application) = AppDatabase.getDatabase(application)

val appModule = module {
    single { provideDatabase(get()) }
}