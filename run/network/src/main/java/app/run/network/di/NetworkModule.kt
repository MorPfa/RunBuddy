package app.run.network.di

import app.core.domain.run.RemoteRunDatasource
import app.run.network.KtorRemoteRunDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDatasource).bind<RemoteRunDatasource>()
}