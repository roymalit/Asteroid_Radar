package com.royma.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.royma.asteroidradar.Asteroid
import com.royma.asteroidradar.TestAsteroid1
import com.royma.asteroidradar.TestAsteroid2
import com.royma.asteroidradar.TestAsteroid3
import com.royma.asteroidradar.repository.AsteroidDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for AsteroidRadarFragment.
 */
class AsteroidRadarViewModel(val database: AsteroidDatabaseDao,
                             application: Application): AndroidViewModel(application) {
    // TODO: Change from using TestAsteroid to MutableLiveData when working

    // Setup like this for testing
    lateinit var allAsteroids: List<Asteroid>
    lateinit var allAsteroidsLiveData: LiveData<List<Asteroid>>

    init {
        viewModelScope.launch {
            setupDummyData()
        }
    }

    /*

     */

    private suspend fun setupDummyData(){
        withContext(Dispatchers.IO){
            clear()
            database.insertAll(listOf(TestAsteroid1, TestAsteroid2, TestAsteroid3, TestAsteroid1))
            Log.i("setupDummyData()", "Database row count: ${database.getRowCount()}")
            allAsteroids = database.getAllAsteroids()
        }
    }

    private suspend fun getImageOfTheDay(){
//        var image = PictureOfDay()
    }

    private suspend fun insert(asteroid: Asteroid){
        withContext(Dispatchers.IO){
            database.insert(asteroid)
        }
    }

    private suspend fun update(asteroid: Asteroid){
        withContext(Dispatchers.IO){
            database.update(asteroid)
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

}