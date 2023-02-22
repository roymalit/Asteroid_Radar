package com.royma.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.royma.asteroidradar.Asteroid
import com.royma.asteroidradar.TestAsteroid1
import com.royma.asteroidradar.TestAsteroid2
import com.royma.asteroidradar.TestAsteroid3
import com.royma.asteroidradar.api.NasaApi
import com.royma.asteroidradar.api.parseAsteroidsJsonResult
import com.royma.asteroidradar.repository.AsteroidDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

/**
 * ViewModel for AsteroidRadarFragment.
 */
class AsteroidRadarViewModel(
    val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var latestAsteroid = MutableLiveData<Asteroid>()

    // Stores the raw data returned from the network API call
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // Stores the list of all the Asteroid objects returned from the database
    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    // Used to track navigation to Detail view
    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail


    // Potential Lint error in androidx.lifecycle. Remove suppression when fixed
    @SuppressLint("NullSafeMutableLiveData")
    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }


    init {
        getNasaAsteroids()
    }

    /**
     * Fills the database with dummy data for testing if database usage is successful before
     * connecting it to an online repo.
     */
    private suspend fun setupDummyData() {
        withContext(Dispatchers.IO) {
            clear()
            database.insertAll(listOf(TestAsteroid1, TestAsteroid2, TestAsteroid3, TestAsteroid1))
            Timber.tag("setupDummyData()").i("Database row count: %s", database.getRowCount())
        }
    }

//    private suspend fun getImageOfTheDay(){
//        var image = PictureOfDay()
//    }

    // Note: Result is a JSON object instead of an array which causes problems
    // Object must be parsed to make it usable by Moshi
    private fun getNasaAsteroids(){
        viewModelScope.launch {
            try {
                val stringResult = NasaApi.retrofitService.getAsteroids()
                // Casts the string result to a JSON object then converts it into a List<Asteroid>
                val parsedResponse = parseAsteroidsJsonResult(JSONObject(stringResult))
                _asteroids.value = parsedResponse
                // storeInOfflineDatabase(parsedResponse)
                Timber.tag("Success").i("${parsedResponse.size} Asteroid properties retrieved")
            } catch (e: Exception){
                _response.value = "Failure: ${e.message}"
                Timber.tag("Failure").e(e)
            }
        }

    }

    private suspend fun insert(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.insert(asteroid)
        }
    }

    private suspend fun insertAll(asteroids: List<Asteroid>) {
        withContext(Dispatchers.IO) {
            database.insertAll(asteroids)
        }
    }

    private suspend fun update(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.update(asteroid)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun storeInOfflineDatabase(list: List<Asteroid>) {
        clear()
        insertAll(list)
    }

    fun onAsteroidClicked(selectedAsteroid: Asteroid){
        _navigateToAsteroidDetail.value = selectedAsteroid
    }
}