package runbuddy.wear.app.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.core.notification.service.ActiveRunService
import app.core.presentation.designsystem_wear.RunBuddyTheme
import runbuddy.wear.run.presentation.TrackerScreenRoot


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            RunBuddyTheme {
                TrackerScreenRoot(
                    onServiceToggle = { shouldStartRunning ->
                        if (shouldStartRunning) {
                            startService(
                                ActiveRunService.createStartIntent(
                                    applicationContext,
                                    this::class.java
                                )
                            )
                        } else {
                            startService(
                                ActiveRunService.createStopIntent(applicationContext)
                            )
                        }

                    }
                )
            }
        }
    }
}




