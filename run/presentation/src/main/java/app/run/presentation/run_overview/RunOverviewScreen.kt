@file:OptIn(ExperimentalMaterial3Api::class)

package app.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.core.presentation.designsystem.AnalyticsIcon
import app.core.presentation.designsystem.LogoIcon
import app.core.presentation.designsystem.LogoutIcon
import app.core.presentation.designsystem.RunBuddyTheme
import app.core.presentation.designsystem.RunIcon
import app.core.presentation.designsystem.components.RunBuddyFloatingActionButton
import app.core.presentation.designsystem.components.RunBuddyScaffold
import app.core.presentation.designsystem.components.RunBuddyToolbar
import app.core.presentation.designsystem.components.util.DropDownItem
import app.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoot(
    viewModel: RunOverviewViewModel = koinViewModel(),
) {
    RunOverviewScreen(
        onAction = viewModel::onAction
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    RunBuddyScaffold(
        topAppBar = {
            RunBuddyToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.runbuddy),
                scrollBehaviour = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            RunBuddyFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RunBuddyTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}