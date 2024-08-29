package app.run.data.di

import app.core.domain.run.SyncRunScheduler
import app.run.data.CreateRunWorker
import app.run.data.DeleteRunWorker
import app.run.data.FetchRunsWorker
import app.run.data.SyncRunWorkerScheduler
import app.run.data.connectivity.PhoneToWatchConnector
import app.run.domain.WatchConnector
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
    singleOf(::PhoneToWatchConnector).bind<WatchConnector>()
}