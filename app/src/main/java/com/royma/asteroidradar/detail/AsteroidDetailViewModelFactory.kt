package com.royma.asteroidradar.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.royma.asteroidradar.database.DatabaseAsteroid
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

/**
 * Simple ViewModel factory that provides the Asteroid object and context to the ViewModel.
 */
class AsteroidDetailViewModelFactory(
    private val asteroid: DatabaseAsteroid,
    private val dataSource: AsteroidDatabaseDao): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidDetailViewModel::class.java)) {
            return AsteroidDetailViewModel(asteroid, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}