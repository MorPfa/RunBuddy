package app.runbuddy.analytics.analytics_feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.core.presentation.designsystem.RunBuddyTheme
import app.runbuddy.analytics.data.di.analyticsModule
import app.runbuddy.analytics.presentation.AnalyticsDashboardScreenRoot
import app.runbuddy.analytics.presentation.di.analyticsPresentationModule
import com.google.android.play.core.splitcompat.SplitCompat
import org.koin.core.context.loadKoinModules

class AnalyticsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(listOf(analyticsModule, analyticsPresentationModule))
        SplitCompat.installActivity(this)
        setContent {
            RunBuddyTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "analytics_dashboard"
                ) {
                    composable(route = "analytics_dashboard") {
                        AnalyticsDashboardScreenRoot(onBackClick = { finish() })
                    }
                }

            }
        }
    }
}