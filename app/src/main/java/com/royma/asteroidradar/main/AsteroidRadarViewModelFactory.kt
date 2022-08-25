package com.royma.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

/**
 * Boiler plate code for a ViewModel Factory.
 *
 * Provides the key for an asteroid and the AsteroidDatabaseDao to the ViewModel.
 */
class AsteroidRadarViewModelFactory (
    private val asteroidKey: Long,
    private val dataSource: AsteroidDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidRadarViewModel::class.java)) {
            return AsteroidRadarViewModel(asteroidKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}