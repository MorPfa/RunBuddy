package runbuddy.wear.app.presentation

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import runbuddy.wear.run.presentation.di.runPresentationModule

class RunBuddyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RunBuddyApp)
            modules(
                runPresentationModule
            )
        }
    }
}