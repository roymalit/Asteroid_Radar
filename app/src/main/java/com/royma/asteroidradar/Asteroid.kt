package com.royma.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize @Entity(tableName = "near_earth_objects")
data class Asteroid(

    @PrimaryKey
    val asteroidId: Long,

    @ColumnInfo(name = "name")
    val codename: String,

    @ColumnInfo(name = "close_approach_data")
    val closeApproachData: String,

    @ColumnInfo(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,

    @ColumnInfo(name = "miss_distance")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
    ) : Parcelable