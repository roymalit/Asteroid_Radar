package com.royma.asteroidradar.domain

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

// Asteroid objects used to make testing easier
val TestAsteroid1 = Asteroid(
    2159928,
    "Testeroid 1",
    "2022-08-03",
    18.05,
    1.4589485569,
    17.7559067373,
    0.2238852542,
    false
)

val TestAsteroid2 = Asteroid(
    2445974,
    "Testeroid 2",
    "2022-07-19",
    20.29,
    0.5200438667,
    19.4510153897,
    0.3948717663,
    true
)

val TestAsteroid3 = Asteroid(
    3290881,
    "Testeroid 3",
    "2022-03-16",
    20.2,
    0.5420507863,
    13.2887839991,
    0.109943137,
    true
)