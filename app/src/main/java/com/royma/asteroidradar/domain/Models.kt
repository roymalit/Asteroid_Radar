package com.royma.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(val nasaSmallBody: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

@Parcelize
data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String) : Parcelable