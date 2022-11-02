package com.royma.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var latestAsteroid = MutableLiveData<Asteroid>()

    // Stores the list of all the Asteroid objects returned from the database
    val allAsteroidsLD = database.getAllAsteroids()

    private val _navigateToAsteroidDetail = MutableLiveData<Long>()
    val navigateToAsteroidDetail: LiveData<Long>
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(id: Long){
        _navigateToAsteroidDetail.value = id
    }

    // Potential Lint error. Remove suppression when fixed
    @SuppressLint("NullSafeMutableLiveData")
    fun onAsteroidDetailNavigated(){
        _navigateToAsteroidDetail.value = null
    }


    init {
        viewModelScope.launch {
            setupDummyData()
        }
    }

    /**
     * Fills the database with dummy data for testing if database usage is successful before
     * connecting it to an online repo.
     */
    private suspend fun setupDummyData(){
        withContext(Dispatchers.IO){
            clear()
            database.insertAll(listOf(TestAsteroid1, TestAsteroid2, TestAsteroid3, TestAsteroid1))
            Log.i("setupDummyData()", "Database row count: ${database.getRowCount()}")
        }
    }

//    private suspend fun getImageOfTheDay(){
//        var image = PictureOfDay()
//    }

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