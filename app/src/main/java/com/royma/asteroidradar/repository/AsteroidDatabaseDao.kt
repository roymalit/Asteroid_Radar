package com.royma.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.royma.asteroidradar.database.DatabaseAsteroid

/**
 * Defines methods for using the Asteroid class with Room.
 */
@Dao
interface AsteroidDatabaseDao {

    @Insert
    fun insert(asteroid: DatabaseAsteroid)

    /**
     * Selects and returns the row that matches the supplied asteroid ID, which is our key.
     *
     * @param key asteroid SPK-ID to match
     */
    @Query("SELECT * FROM asteroid_radar_database WHERE id = :key")
    fun get(key: Long): DatabaseAsteroid?

    /**
     * Inserts all passed-in asteroid objects at once.
     *
     * @param asteroids an [Array] of Asteroid objects.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    /**
     * Selects and returns all rows in the table from today's date,
     *
     * sorted by date in ascending order. 7 day coverage by default.
     */
    @Query("SELECT * FROM asteroid_radar_database WHERE close_approach_date >= date('now','localtime') ORDER BY close_approach_date ASC")
    fun getWeeksAsteroids(): LiveData<List<DatabaseAsteroid>>

    /**
     * Selects and returns all rows in the table that match today's date
     */
    @Query("SELECT * FROM asteroid_radar_database WHERE close_approach_date == date('now','localtime') ORDER BY close_approach_date ASC")
    fun getTodaysAsteroids(): LiveData<List<DatabaseAsteroid>>

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM asteroid_radar_database")
    fun clear()

    /**
     * Selects and returns the latest asteroid.
     */
    @Query("SELECT * FROM asteroid_radar_database ORDER BY close_approach_date DESC LIMIT 1")
    fun getLatestAsteroid(): DatabaseAsteroid?

    /**
     * Get the row count from the table
     */
    @Query("SELECT COUNT(id) FROM asteroid_radar_database")
    fun getRowCount(): Int

}
