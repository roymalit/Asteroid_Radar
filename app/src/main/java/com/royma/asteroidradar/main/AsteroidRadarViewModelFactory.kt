package com.royma.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

/**
 * Boiler plate code for a ViewModel Factory.
 *
 * Provides the key for an asteroid and the AsteroidDatabaseDao to the ViewModel.
 */
class AsteroidRadarViewModelFactory(
    private val dataSource: AsteroidDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidRadarViewModel::class.java)) {
            return AsteroidRadarViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}