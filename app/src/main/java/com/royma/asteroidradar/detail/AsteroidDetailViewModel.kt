package com.royma.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.royma.asteroidradar.database.DatabaseAsteroid
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

class AsteroidDetailViewModel(asteroid: DatabaseAsteroid, val database: AsteroidDatabaseDao): ViewModel() {

    private val _selectedAsteroid = MutableLiveData<DatabaseAsteroid>()
    val selectedAsteroid: LiveData<DatabaseAsteroid>
            get() = _selectedAsteroid

    init {
        _selectedAsteroid.value = asteroid
    }
}