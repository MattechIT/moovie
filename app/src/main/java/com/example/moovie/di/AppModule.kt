package com.example.moovie.di

import com.example.moovie.data.repository.AuthRepository
import com.example.moovie.data.repository.MockAuthRepository
import org.koin.dsl.module

val appModule = module {
    // Auth repository implementation
    single<AuthRepository> { MockAuthRepository() }
}
