package app.runbuddy.analytics.domain

import kotlin.time.Duration

data class AnalyticsValue(
    val totalDistanceRun : Int = 0,
    val totalTimRun : Duration = Duration.ZERO,
    val fastestEverRun : Double = 0.0,
    val avgPacePerRun : Double = 0.0,
    val avgDistancePerRun : Double = 0.0,
)
