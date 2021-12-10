package com.dikutenz.truthtables.di

import com.dikutenz.truthtables.model.repositories.BooleanFunctionRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        BooleanFunctionRepository(get())
    }
}