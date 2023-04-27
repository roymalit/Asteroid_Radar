package com.royma.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.royma.asteroidradar.domain.Asteroid
import com.royma.asteroidradar.repository.AsteroidDatabaseDao

class AsteroidDetailViewModel(asteroid: Asteroid, val database: AsteroidDatabaseDao): ViewModel() {

    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
            get() = _selectedAsteroid

    init {
        _selectedAsteroid.value = asteroid
    }
}