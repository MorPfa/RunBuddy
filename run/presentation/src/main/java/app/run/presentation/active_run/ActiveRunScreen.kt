@file:OptIn(ExperimentalMaterial3Api::class)

package app.run.presentation.active_run

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.core.presentation.designsystem.RunBuddyTheme
import app.core.presentation.designsystem.StartIcon
import app.core.presentation.designsystem.StopIcon
import app.core.presentation.designsystem.components.RunBuddyFloatingActionButton
import app.core.presentation.designsystem.components.RunBuddyScaffold
import app.core.presentation.designsystem.components.RunBuddyToolbar
import app.run.presentation.R
import app.run.presentation.active_run.components.RunDataCard
import app.run.presentation.util.shouldShowLocationPermissionRationale
import app.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel


@Composable
fun ActiveRunScreenRoot(viewModel: ActiveRunViewModel = koinViewModel()) {
    ActiveRunScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}


@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit,
) {
    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()) {perms ->
            val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
            val hasNotificationPermission = if(Build.VERSION.SDK_INT >= 33){
                perms[Manifest.permission.POST_NOTIFICATIONS] == true
            }else {
                true
            }
            val activity = context as ComponentActivity
            val showLocationRationale = activity.shouldShowLocationPermissionRationale()
            val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()
        }
    RunBuddyScaffold(withGradient = false,
        topAppBar = {
            RunBuddyToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.active_run),
                onBackClick = {
                    onAction(ActiveRunAction.OnBackClick)
                },

                )
        },
        floatingActionButton = {
            RunBuddyFloatingActionButton(
                icon = if (state.shouldTrack) StopIcon else StartIcon,
                onClick = { onAction(ActiveRunAction.OnToggleRunClick) },
                iconSize = 20.dp,
                contentDescription =
                if (state.shouldTrack) stringResource(id = R.string.pause_run)
                else stringResource(id = R.string.start_run)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(padding)
                    .fillMaxWidth()
            )
        }

    }

}


@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RunBuddyTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}