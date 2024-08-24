package app.runbuddy.analytics.data.di

import app.core.database.RunDatabase
import app.runbuddy.analytics.data.AnalyticsRepositoryImpl
import app.runbuddy.analytics.domain.AnalyticsRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val analyticsModule = module {
    singleOf(::AnalyticsRepositoryImpl).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}