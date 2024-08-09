package app.auth.presentation.di

import app.auth.presentation.login.LoginViewModel
import app.auth.presentation.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module{
  viewModelOf(::RegistrationViewModel)
  viewModelOf(::LoginViewModel)

}