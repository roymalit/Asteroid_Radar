package com.royma.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.royma.asteroidradar.api.NasaApi
import com.royma.asteroidradar.database.DatabaseAsteroid
import com.royma.asteroidradar.domain.*
import com.royma.asteroidradar.repository.AsteroidDatabase
import com.royma.asteroidradar.repository.AsteroidDatabaseDao
import com.royma.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

enum class AsteroidFilter {
    WEEK, TODAY, SAVED;
}
/**
 * ViewModel for AsteroidRadarFragment.
 */
class AsteroidRadarViewModel(val database: AsteroidDatabaseDao,
                             application: Application) : AndroidViewModel(application) {

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

    // Used to track which filter is being applied to the Asteroid list
    private val filterAsteroids = MutableLiveData<AsteroidFilter>()


    // Potential Lint error in androidx.lifecycle. Remove suppression when fixed
    @SuppressLint("NullSafeMutableLiveData")
    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    init {
        try {
            if (isConnected(application.applicationContext)){
                filterAsteroids.value = AsteroidFilter.WEEK
                getImageOfTheDay()
                viewModelScope.launch {
                    asteroidRepository.refreshAsteroids()
                }
            }
        } catch (e: IOException){
            Timber.tag("NETWORK_ERROR").d(e)
        }
    }

    val asteroidCollection = filterAsteroids.switchMap{
        asteroidRepository.getFilteredAsteroids(it)
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

    // Updates currently applied filter
    fun updateFilter(asteroidFilter: AsteroidFilter){
        filterAsteroids.value = asteroidFilter
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onAsteroidClicked(selectedAsteroid: Asteroid){
        _navigateToAsteroidDetail.value = selectedAsteroid
    }

    /*
     * Methods for checking for network connection
     */
    @Suppress("DEPRECATION")
    fun isConnectedOld(context: Context): Boolean {
        val connManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        Timber.tag("NETWORK STATUS OLD").d("Network available: ${networkInfo?.isConnected}")
        return networkInfo?.isConnected ?: false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnectedNewApi(context: Context): Boolean {
        val cm = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)?: return false
        val capResult = checkAllCapabilities(capabilities)
        Timber.tag("NETWORK STATUS NEW").d("Network available: $capResult")
        return capResult
    }

    /**
     * Checks if network connection is available.
     */
    private fun isConnected(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isConnectedNewApi(context)
        } else{
            isConnectedOld(context)
        }
    }

    /**
     * Checks various network capabilities to insure a connection is possible.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkAllCapabilities (capabilities: NetworkCapabilities) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED) &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        }
}