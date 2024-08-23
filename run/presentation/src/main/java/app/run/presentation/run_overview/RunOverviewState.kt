package app.run.presentation.run_overview

import app.run.presentation.run_overview.model.RunUi

data class RunOverviewState(
    val runs : List<RunUi> = emptyList()
)
