package com.royma.asteroidradar.api

import com.royma.asteroidradar.database.DatabaseAsteroid
import com.royma.asteroidradar.domain.Asteroid
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. Should convert these to domain objects before
 * using them.
 */

/**
 * AsteroidContainer holds a list of Asteroids.
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)

/**
 * Asteroid represents a single NASA asteroid object
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroid(val nasaSmallBody: Long, val codename: String, val closeApproachDate: String,
                           val absoluteMagnitude: Double, val estimatedDiameter: Double,
                           val relativeVelocity: Double, val distanceFromEarth: Double,
                           val isPotentiallyHazardous: Boolean)

/**
 * Convert Network results to domain objects
 */
fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid> {
    return asteroids.map {
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

/**
 * Converts a [List] of database objects into an [Array] of objects
 */
fun List<DatabaseAsteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {

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
    }.toTypedArray()
}