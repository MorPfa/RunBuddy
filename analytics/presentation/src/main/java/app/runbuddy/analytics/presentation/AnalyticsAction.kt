package app.runbuddy.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick : AnalyticsAction

}