package app.runbuddy

import android.app.Application
import android.content.Context
import app.auth.data.di.authDataModule
import app.auth.presentation.di.authViewModelModule
import app.core.connectivity.data.di.coreConnectivityDataModule
import app.core.data.di.coreDataModule
import app.core.database.di.databaseModule
import app.run.data.di.runDataModule
import app.run.location.di.locationModule
import app.run.network.di.networkModule
import app.run.presentation.di.runPresentationModule
import app.runbuddy.di.appModule
import com.google.android.play.core.splitcompat.SplitCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class RunBuddyApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@RunBuddyApp)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule,
                coreConnectivityDataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}