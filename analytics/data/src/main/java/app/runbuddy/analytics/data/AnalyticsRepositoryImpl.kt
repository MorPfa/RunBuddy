package app.runbuddy.analytics.data

import app.core.database.dao.AnalyticsDao
import app.runbuddy.analytics.domain.AnalyticsRepository
import app.runbuddy.analytics.domain.AnalyticsValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class AnalyticsRepositoryImpl(private val analyticsDao: AnalyticsDao) : AnalyticsRepository {

    override suspend fun getAnalyticsValue(): AnalyticsValue {
        return withContext(Dispatchers.IO) {
            val totalDistance = async { analyticsDao.getTotalDistance() }
            val totalTimeMillis = async { analyticsDao.getTotalTimeRun() }
            val maxRunSpeed = async { analyticsDao.getMaxRunSpeed() }
            val avgDistancePerRun = async { analyticsDao.getAvgDistancePerRun() }
            val avgPacePerRun = async { analyticsDao.getAvgPacePerRun() }
            AnalyticsValue(
                totalDistanceRun = totalDistance.await(),
                totalTimRun = totalTimeMillis.await().milliseconds,
                fastestEverRun = maxRunSpeed.await(),
                avgPacePerRun = avgPacePerRun.await(),
                avgDistancePerRun = avgDistancePerRun.await()

            )
        }
    }
}