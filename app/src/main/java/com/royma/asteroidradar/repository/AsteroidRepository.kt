package com.royma.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.royma.asteroidradar.api.NasaApi
import com.royma.asteroidradar.api.asDatabaseModel
import com.royma.asteroidradar.api.parseAsteroidsJsonResult
import com.royma.asteroidradar.database.asDomainModel
import com.royma.asteroidradar.domain.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository (private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getAsteroids()){
        it.asDomainModel()
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val asteroidList = parseAsteroidsJsonResult(
                    JSONObject(NasaApi.retrofitService.getAsteroids()))
            database.asteroidDatabaseDao.insertAll(*asteroidList.asDatabaseModel())
        }
    }
}