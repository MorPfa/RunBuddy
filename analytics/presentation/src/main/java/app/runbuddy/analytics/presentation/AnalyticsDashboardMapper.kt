package app.runbuddy.analytics.presentation

import app.core.presentation.ui.formatted
import app.core.presentation.ui.toFormattedKm
import app.core.presentation.ui.toFormattedKmh
import app.core.presentation.ui.toFormattedMeters
import app.core.presentation.ui.toFormattedPace
import app.runbuddy.analytics.domain.AnalyticsValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun Duration.toFormattedTotalTime() : String {
    val days = toLong(DurationUnit.DAYS)
    val hours = toLong(DurationUnit.HOURS) % 24
    val minutes = toLong(DurationUnit.MINUTES) % 60
    return "${days}d ${hours}h ${minutes}m"
}

fun AnalyticsValue.toAnalyticsDashboardState() : AnalyticsDashboardState {
    return AnalyticsDashboardState(
        totalDistanceRun = (totalDistanceRun / 1000.0 ).toFormattedKm(),
        totalTimeRun = totalTimRun.toFormattedTotalTime(),
        avgDistance = (avgDistancePerRun / 1000.0 ).toFormattedKm(),
        avgPace = avgPacePerRun.seconds.formatted(),
        fastestEverRun = fastestEverRun.toFormattedKmh()

    )
}