package runbuddy.wear.app.presentation

import android.app.Application
import app.core.connectivity.data.di.coreConnectivityDataModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import runbuddy.wear.app.presentation.di.appModule
import runbuddy.wear.run.data.di.wearRunDataModule
import runbuddy.wear.run.presentation.di.wearRunPresentationModule

class RunBuddyApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RunBuddyApp)
            modules(
                appModule,
                wearRunPresentationModule,
                wearRunDataModule,
                coreConnectivityDataModule,

            )
        }
    }
}