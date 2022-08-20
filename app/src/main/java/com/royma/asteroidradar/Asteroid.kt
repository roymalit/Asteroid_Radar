package com.royma.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize @Entity(tableName = "near_earth_objects")
data class Asteroid(

    // NASA JPL small body (SPK-ID) ID
    @ColumnInfo(name = "id")
    val nasaSmallBody: Long,

    // Designated name
    @ColumnInfo(name = "name")
    val codename: String,

    @ColumnInfo(name = "close_approach_data")
    val closeApproachData: String,

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

// Asteroid object used to make testing easier
val TestAsteroid = Asteroid(
    2159928,
    "Testeroid",
    "All Close Approach Data",
    18.05,
    1.4589485569,
    17.7559067373,
    0.2238852542,
    false
)