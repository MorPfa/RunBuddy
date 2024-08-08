package app.auth.data.di

import app.auth.data.AuthRepositoryImpl
import app.auth.data.EmailPatternValidator
import app.auth.domain.AuthRepository
import app.auth.domain.PatternValidator
import app.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module{
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}