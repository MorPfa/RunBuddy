package app.runbuddy.analytics.domain

interface AnalyticsRepository {
    suspend fun getAnalyticsValue() : AnalyticsValue
}