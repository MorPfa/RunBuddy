package app.runbuddy


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import app.auth.presentation.intro.IntroScreenRoot
import app.auth.presentation.login.LoginScreenRoot
import app.auth.presentation.registration.RegistrationScreenRoot
import app.run.presentation.active_run.ActiveRunScreenRoot
import app.run.presentation.run_overview.RunOverviewScreenRoot

@Composable
fun NavigationRoot(
    onAnalyticsClick: () -> Unit,
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "run" else "auth"
    ) {
        authGraph(navController)
        runGraph(navController = navController, onAnalyticsClick = onAnalyticsClick)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "register") {
            RegistrationScreenRoot(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )
        }
        composable("login") {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(
    navController: NavHostController,
    onAnalyticsClick: () -> Unit,
) {
    navigation(startDestination = "run_overview", route = "run") {
        composable("run_overview") {
            RunOverviewScreenRoot(
                onAnalyticsClick = onAnalyticsClick,
                onStartRunClick = {
                    navController.navigate("active_run")
                },
                onLogoutClick = {
                    navController.navigate("auth") {
                        popUpTo("run") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "active_run",
            deepLinks = listOf(navDeepLink { uriPattern = "runbuddy://active_run" })
        ) {
            val context = LocalContext.current
            ActiveRunScreenRoot(onServiceToggle = { shouldServiceRun ->
                if (shouldServiceRun) {
                    context.startService(
                        app.core.notification.service.ActiveRunService.createStartIntent(
                            context = context,
                            activity = MainActivity::class.java
                        )
                    )
                } else {
                    context.startService(
                        app.core.notification.service.ActiveRunService.createStopIntent(
                            context = context,
                        )
                    )
                }
            },
                onBack = {
                    navController.navigateUp()
                }, onFinish = {
                    navController.navigateUp()

                }
            )
        }

    }
}