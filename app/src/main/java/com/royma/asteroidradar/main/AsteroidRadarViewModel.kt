package com.royma.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.royma.asteroidradar.api.NasaApi
import com.royma.asteroidradar.api.parseAsteroidsJsonResult
import com.royma.asteroidradar.database.DatabaseAsteroid
import com.royma.asteroidradar.domain.*
import com.royma.asteroidradar.repository.AsteroidDatabase
import com.royma.asteroidradar.repository.AsteroidDatabaseDao
import com.royma.asteroidradar.repository.AsteroidRepository
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

    private var latestAsteroid = MutableLiveData<DatabaseAsteroid>()

    // Create new database
    private val newDatabase = AsteroidDatabase.getInstance(application)
    // Create asteroids repository
    private val asteroidRepository = AsteroidRepository(newDatabase)

    // Stores the raw data returned from the network API call
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    // Stores the list of all the Asteroid objects returned from the database
    private val _asteroids = MutableLiveData<List<DatabaseAsteroid>>()
    val asteroids: LiveData<List<DatabaseAsteroid>>
        get() = _asteroids

    // Stores the Astronomy picture of the day
    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

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
        getImageOfTheDay()
        // getNasaAsteroids()
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    val asteroidCollection = asteroidRepository.asteroids

    /**
     * Fills the database with dummy data for testing if database usage is successful before
     * connecting it to an online repo.
     */
    private suspend fun setupDummyData() {
        withContext(Dispatchers.IO) {
            clear()
            database.insertAll(TestAsteroid1, TestAsteroid2, TestAsteroid3, TestAsteroid1)
            Timber.tag("setupDummyData()").i("Database row count: %s", database.getRowCount())
        }
    }

    private fun getImageOfTheDay(){
        viewModelScope.launch {
            try {
                val picObject = NasaApi.retrofitService.getPictureOfDay()
                _picOfDay.value = picObject
                _status.value = "Success! Picture of the day '${picObject.title}' retrieved"
            } catch (e: Exception){
                _status.value = "Failure: ${e.message}"
                Timber.tag("Failure").e(e)
            }
        }
    }

    // Note: Result is a JSON object instead of an array which causes problems
    // Object must be parsed to make it usable by Moshi
    private fun getNasaAsteroids(){
        viewModelScope.launch {
            try {
                val stringResult = NasaApi.retrofitService.getAsteroids()
                // Casts the string result to a JSON object then converts it into a List<Asteroid>
                val parsedResponse = parseAsteroidsJsonResult(JSONObject(stringResult))
                _status.value = "Success: ${parsedResponse.size} Asteroid properties retrieved"
                _asteroids.value = parsedResponse
                // storeInOfflineDatabase(parsedResponse)
                Timber.tag("Success").i("${parsedResponse.size} Asteroid properties retrieved")
            } catch (e: Exception){
                _status.value = "Failure: ${e.message}"
                Timber.tag("Failure").e(e)
            }
        }

    }

    private suspend fun insert(asteroid: DatabaseAsteroid) {
        withContext(Dispatchers.IO) {
            database.insert(asteroid)
        }
    }

    private suspend fun insertAll(asteroids: DatabaseAsteroid) {
        withContext(Dispatchers.IO) {
            database.insertAll(asteroids)
        }
    }

    private suspend fun update(asteroid: DatabaseAsteroid) {
        withContext(Dispatchers.IO) {
            database.update(asteroid)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onAsteroidClicked(selectedAsteroid: Asteroid){
        _navigateToAsteroidDetail.value = selectedAsteroid
    }
}