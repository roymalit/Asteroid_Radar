package com.royma.asteroidradar.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.royma.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {

    @Insert
    suspend fun insert(asteroid: Asteroid)

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
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from near_earth_objects WHERE asteroidId = :key")
    suspend fun get(key: Long): Asteroid?


}
