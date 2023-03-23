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
    suspend fun insert(asteroid: DatabaseAsteroid)

    /**
     * Selects and returns the row that matches the supplied asteroid ID, which is our key.
     *
     * @param key asteroidId to match
     */
    @Query("SELECT * FROM asteroid_radar_database WHERE asteroidId = :key")
    suspend fun get(key: Long): DatabaseAsteroid?

    /**
     * Inserts all passed-in asteroid objects at once.
     *
     * @param asteroids an [Array] of Asteroid objects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: DatabaseAsteroid)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(objects: List<Asteroid>)

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by date in descending order.
     */
    @Query("SELECT * FROM asteroid_radar_database")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>
//    @Query("SELECT * FROM asteroid_radar_database ORDER BY asteroidId DESC") // Used for local/network
//    fun getAllAsteroids(): LiveData<List<Asteroid>>

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param asteroid new value to write
     */
    @Update
    suspend fun update(asteroid: DatabaseAsteroid)

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM asteroid_radar_database")
    suspend fun clear()

    /**
     * Selects and returns the latest asteroid.
     */
    @Query("SELECT * FROM asteroid_radar_database ORDER BY asteroidId DESC LIMIT 1")
    suspend fun getLatestAsteroid(): DatabaseAsteroid?

    /**
     * Get the row count from the table
     */
    @Query("SELECT COUNT(id) FROM asteroid_radar_database")
    suspend fun getRowCount(): Int

}
