package app.run.network.mappers

import app.core.domain.location.Location
import app.core.domain.run.Run
import app.run.network.CreateRunRequest
import app.run.network.RunDto
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Duration.Companion.milliseconds

fun RunDto.toRun(): Run {
    return Run(
        id = id,
        duration = durationMillis.milliseconds,
        dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC")),
        distanceMeters = distanceMeters,
        location = Location(lat = lat, lon = long),
        totalElevationMeters = totalElevationMeters,
        mapPictureUrl = mapPictureUrl,
        maxSpeedKmh = maxSpeedKmh,
        avgHeartRate = avgHeartRate,
        maxHeartRate = maxHeartRate
    )
}


fun Run.toCreateRunRequest(): CreateRunRequest {
    return CreateRunRequest(
        id = id!!,
        durationMillis = duration.inWholeMilliseconds,
        distanceMeters = distanceMeters,
        long = location.lon,
        lat = location.lat,
        totalElevationMeters = totalElevationMeters,
        maxSpeedKmh = maxSpeedKmh,
        avgSpeedKmh = avgSpeedKmh,
        epochMillis = dateTimeUtc.toEpochSecond() * 1000L,
        avgHeartRate = avgHeartRate,
        maxHeartRate = maxHeartRate
    )
}