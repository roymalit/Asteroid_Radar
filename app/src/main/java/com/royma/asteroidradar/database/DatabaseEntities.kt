package com.royma.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.royma.asteroidradar.domain.Asteroid
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "asteroid_radar_database")
data class DatabaseAsteroid constructor(

    // NASA JPL small body (SPK-ID) ID
    @PrimaryKey
    @ColumnInfo(name = "id")
    val nasaSmallBody: Long,

    // Designated name
    @ColumnInfo(name = "name")
    val codename: String,

    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,

    @ColumnInfo(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,

    // Kilometers MAX
    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double,

    // Kilometers per second
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,

    // Astronomical distance
    @ColumnInfo(name = "miss_distance")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean,

) : Parcelable

/**
 * converts from database objects to domain objects
 */
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            nasaSmallBody = it.nasaSmallBody,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
