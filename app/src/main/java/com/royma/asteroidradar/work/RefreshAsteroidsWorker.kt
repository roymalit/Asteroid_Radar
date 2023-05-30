package com.royma.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.royma.asteroidradar.repository.AsteroidDatabase
import com.royma.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshAsteroidsWorker (appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException){
            Result.retry()
        }
    }

}