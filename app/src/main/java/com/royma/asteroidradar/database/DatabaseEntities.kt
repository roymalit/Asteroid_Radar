package com.royma.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "asteroid_radar_database")
data class DatabaseAsteroid(

    // NASA JPL small body (SPK-ID) ID
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

    // Set last to avoid having to name as argument
    @PrimaryKey(autoGenerate = true)
    val asteroidId: Long = 0L
) : Parcelable

/**
 * converts from database objects to domain objects
 */
fun List<DatabaseAsteroid>.asDomainModel(): List<DatabaseAsteroid> {
    return map {
        DatabaseAsteroid(
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