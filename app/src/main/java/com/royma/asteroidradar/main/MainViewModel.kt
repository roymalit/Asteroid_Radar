package com.royma.asteroidradar.main

import androidx.lifecycle.ViewModel
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

/**
 * ViewModel for SleepQualityFragment.
 *
 * @param asteroidKey The key of the current asteroid we are working with.
 */
class MainViewModel(private val asteroidKey: Long = 0L,
                    dataSource: AsteroidDatabaseDao): ViewModel() {
    /**
     * Hold a reference to AsteroidDatabase via its AsteroidDatabaseDao.
     */
    val database = dataSource

}