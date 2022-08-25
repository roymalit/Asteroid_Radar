package com.royma.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

/**
 * Boiler plate code for a ViewModel Factory.
 *
 * Provides the key for an asteroid and the AsteroidDatabaseDao to the ViewModel.
 */
class MainViewModelFactory (
    private val asteroidKey: Long,
    private val dataSource: AsteroidDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(asteroidKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}