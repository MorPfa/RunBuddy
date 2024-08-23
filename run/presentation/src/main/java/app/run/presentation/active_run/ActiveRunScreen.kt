@file:OptIn(ExperimentalMaterial3Api::class)

package app.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.core.presentation.designsystem.RunBuddyTheme
import app.core.presentation.designsystem.StartIcon
import app.core.presentation.designsystem.StopIcon
import app.core.presentation.designsystem.components.RunBuddyActionButton
import app.core.presentation.designsystem.components.RunBuddyDialog
import app.core.presentation.designsystem.components.RunBuddyFloatingActionButton
import app.core.presentation.designsystem.components.RunBuddyOutlinedActionButton
import app.core.presentation.designsystem.components.RunBuddyScaffold
import app.core.presentation.designsystem.components.RunBuddyToolbar
import app.core.presentation.ui.ObserveAsEvents
import app.run.presentation.R
import app.run.presentation.active_run.components.RunDataCard
import app.run.presentation.active_run.maps.TrackerMap
import app.run.presentation.active_run.service.ActiveRunService
import app.run.presentation.util.hasLocationPermission
import app.run.presentation.util.hasNotificationPermission
import app.run.presentation.util.shouldShowLocationPermissionRationale
import app.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream


@Composable
fun ActiveRunScreenRoot(
    onFinish: () -> Unit,
    onBack: () -> Unit,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            is ActiveRunEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            ActiveRunEvent.RunSaved -> onFinish()
        }
    }
    ActiveRunScreen(
        state = viewModel.state,
        onServiceToggle = onServiceToggle,
        onAction = { action ->
            when(action) {
                is ActiveRunAction.OnBackClick -> {
                    if(!viewModel.state.hasStartedRunning) {
                        onBack()
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val hasCourseLocationPermission = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationRationale = showNotificationRationale
            )
        )
    }

    LaunchedEffect(key1 = true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationRationale = showNotificationRationale
            )
        )

        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRunBuddyPermissions(context)
        }
    }

    LaunchedEffect(key1 = state.isRunFinished) {
        if (state.isRunFinished) {
            onServiceToggle(false)
        }
    }

    LaunchedEffect(key1 = state.shouldTrack) {
        if (context.hasLocationPermission() && state.shouldTrack && !ActiveRunService.isServiceActive) {
            onServiceToggle(true)
        }
    }

    RunBuddyScaffold(
        withGradient = false,
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
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TrackerMap(
                isRunFinished = state.isRunFinished,
                currentLocation = state.currentLocation,
                locations = state.runData.locations,
                onSnapshot = { bmp ->
                    val stream = ByteArrayOutputStream()
                    stream.use {
                        bmp.compress(
                            Bitmap.CompressFormat.JPEG,
                            80,
                            it
                        )
                    }
                    onAction(ActiveRunAction.OnRunProcessed(stream.toByteArray()))
                },
                modifier = Modifier
                    .fillMaxSize()
            )
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

    if (!state.shouldTrack && state.hasStartedRunning) {
        RunBuddyDialog(
            title = stringResource(id = R.string.run_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(id = R.string.resume_or_finish_run),
            primaryButton = {
                RunBuddyActionButton(
                    text = stringResource(id = R.string.resume),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.OnResumeRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            },
            secondaryButton = {
                RunBuddyOutlinedActionButton(
                    text = stringResource(id = R.string.finish),
                    isLoading = state.isSavingRun,
                    onClick = {
                        onAction(ActiveRunAction.OnFinishRunClick)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        )
    }

    if (state.showLocationRationale || state.showNotificationRationale) {
        RunBuddyDialog(
            title = stringResource(id = R.string.permission_required),
            onDismiss = { /* Normal dismissing not allowed for permissions */ },
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            primaryButton = {
                RunBuddyOutlinedActionButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.OnDismissRationaleDialog)
                        permissionLauncher.requestRunBuddyPermissions(context)
                    },
                )
            }
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRunBuddyPermissions(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermission)
        }

        !hasLocationPermission -> launch(locationPermissions)
        !hasNotificationPermission -> launch(notificationPermission)
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RunBuddyTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onServiceToggle = {},
            onAction = {}
        )
    }
}