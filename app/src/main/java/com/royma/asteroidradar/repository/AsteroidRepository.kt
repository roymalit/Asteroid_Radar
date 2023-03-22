package com.royma.asteroidradar.repository

import com.royma.asteroidradar.api.NasaApi
import com.royma.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository (private val database: AsteroidDatabase) {

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val latestAsteroids = parseAsteroidsJsonResult(
                    JSONObject(NasaApi.retrofitService.getAsteroids()))
            database.asteroidDatabaseDao.insertAll(latestAsteroids)
        }
    }
}