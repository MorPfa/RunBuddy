package app.runbuddy

import android.app.Application
import app.auth.data.di.authDataModule
import app.auth.presentation.di.authViewModelModule
import app.core.data.di.coreDataModule
import app.run.presentation.di.runViewModelModule
import app.runbuddy.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunBuddyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@RunBuddyApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runViewModelModule
            )
        }
    }
}