package app.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity(tableName = "runs")
data class RunEntity(
    val durationMillis: Long,
    val distanceMeters: Int,
    val dateTimeUtc: String,
    val lat: Double,
    val lon: Double,
    val avgSpeedKmh: Double,
    val maxSpeedKmh: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?,
    val maxHeartRate : Int?,
    val avgHeartRate : Int?,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)
