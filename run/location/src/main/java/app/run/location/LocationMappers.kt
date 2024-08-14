package app.run.location

import android.location.Location
import app.core.domain.location.LocationWithAltitude
import app.core.domain.location.Location as LocationDto

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = LocationDto(
            lat = latitude,
            lon = longitude
        ),
        altitude = altitude
    )
}