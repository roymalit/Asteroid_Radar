package com.royma.asteroidradar.repository

import androidx.room.*
import com.royma.asteroidradar.Asteroid

/**
 * Defines methods for using the Asteroid class with Room.
 */
@Dao
interface AsteroidDatabaseDao {

    @Insert
    suspend fun insert(asteroid: Asteroid)

    /**
     * Inserts all passed in asteroid objects at once.
     *
     * @param objects a [List] of Asteroid objects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<Asteroid>)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param asteroid new value to write
     */
    @Update
    suspend fun update(asteroid: Asteroid)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM near_earth_objects")
    suspend fun clear()

    /**
     * Selects and returns the row that matches the supplied asteroid ID, which is our key.
     *
     * @param key asteroidId to match
     */
    @Query("SELECT * FROM near_earth_objects WHERE asteroidId = :key")
    suspend fun get(key: Long): Asteroid?

    /**
     * Selects and returns the latest asteroid.
     */
    @Query("SELECT * FROM near_earth_objects LIMIT 1")
    suspend fun getLatestAsteroid(): Asteroid?

    /**
     * Get the row count from the table
     */
    @Query("SELECT COUNT(id) FROM near_earth_objects")
    suspend fun getRowCount(): Int

}
