package com.dikutenz.truthtables.di

import com.dikutenz.truthtables.viewModel.HistoryViewModel
import com.dikutenz.truthtables.viewModel.MainViewModel
import com.dikutenz.truthtables.viewModel.TheoryViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel() }
    single { TheoryViewModel() }
    single { HistoryViewModel(get()) }
}