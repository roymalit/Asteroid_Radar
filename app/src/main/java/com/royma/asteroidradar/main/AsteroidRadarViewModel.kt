package com.royma.asteroidradar.main

import androidx.lifecycle.ViewModel
import com.royma.asteroidradar.TestAsteroid1
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param asteroidKey The key of the current asteroid we are working with.
 */
class AsteroidRadarViewModel(private val asteroidKey: Long = 0L,
                             dataSource: AsteroidDatabaseDao): ViewModel() {
    /**
     * Hold a reference to AsteroidDatabase via its AsteroidDatabaseDao.
     */
    val database = dataSource

    // TODO: Change from using TestAsteroid to MutableLiveData when working
    private var asteroid = TestAsteroid1


}