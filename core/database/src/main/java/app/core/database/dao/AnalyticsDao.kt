package app.core.database.dao

import androidx.room.Dao
import androidx.room.Query


@Dao
interface AnalyticsDao {

    @Query("SELECT SUM(distanceMeters) FROM runs")
    suspend fun getTotalDistance() : Int
    @Query("SELECT SUM(durationMillis) FROM runs")
    suspend fun getTotalTimeRun() : Long
    @Query("SELECT SUM(maxSpeedKmh) FROM runs")
    suspend fun getMaxRunSpeed() : Double

    @Query("SELECT AVG(distanceMeters) FROM runs")
    suspend fun getAvgDistancePerRun() : Double

    @Query("SELECT AVG((durationMillis / 60000.0) / (distanceMeters / 1000.0)) FROM runs")
    suspend fun getAvgPacePerRun() : Double
}