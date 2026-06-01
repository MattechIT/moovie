package com.example.moovie.di

import com.example.moovie.data.repository.AuthRepository
import com.example.moovie.data.repository.MockAuthRepository
import com.example.moovie.presentation.auth.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Auth repository implementation
    single<AuthRepository> { MockAuthRepository() }
    
    // Auth ViewModel
    viewModel { AuthViewModel(get()) }
}
