package com.royma.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.royma.asteroidradar.api.NasaApi
import com.royma.asteroidradar.api.asDatabaseModel
import com.royma.asteroidradar.api.parseAsteroidsJsonResult
import com.royma.asteroidradar.database.asDomainModel
import com.royma.asteroidradar.domain.Asteroid
import com.royma.asteroidradar.main.AsteroidFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository (private val database: AsteroidDatabase) {

    // Tracks the results of fetching the week's asteroids
    private val weeksAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getWeeksAsteroids()){
        it.asDomainModel()
    }

    // // Tracks the results of fetching today's asteroids
    private val todaysAsteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getTodaysAsteroids()){
                it.asDomainModel()
            }

    fun getFilteredAsteroids(filter: AsteroidFilter): LiveData<List<Asteroid>> {
        return when (filter){
            AsteroidFilter.TODAY -> todaysAsteroids
            else -> weeksAsteroids
        }
    }

    // Refreshes the list of asteroids stored in the database
    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val asteroidList = parseAsteroidsJsonResult(
                    JSONObject(NasaApi.retrofitService.getAsteroids()))
            database.asteroidDatabaseDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }
}