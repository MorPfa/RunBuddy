package app.core.data.di


import app.core.data.auth.EncryptedSessionStorage
import app.core.data.networking.HttpClientFactory
import app.core.data.run.RunRepositoryImpl
import app.core.domain.SessionStorage
import app.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::RunRepositoryImpl).bind<RunRepository>()
}
