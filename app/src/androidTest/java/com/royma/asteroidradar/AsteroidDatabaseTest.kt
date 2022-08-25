package com.royma.asteroidradar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.royma.asteroidradar.repository.AsteroidDatabase
import com.royma.asteroidradar.repository.AsteroidDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AsteroidDatabaseTest {

    private lateinit var asteroidDao: AsteroidDatabaseDao
    private lateinit var db: AsteroidDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AsteroidDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        asteroidDao = db.asteroidDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    // runBlocking required to prevent 'suspend' errors
     fun insertAndGetAsteroid() = runBlocking{
        val asteroid = TestAsteroid
        asteroidDao.insert(asteroid)
        val latest = asteroidDao.getLatestAsteroid()
        assertEquals(latest?.codename, "Testeroid")
    }

    @Test
    @Throws(Exception::class)
    fun insertAllAsteroids() = runBlocking{
        val asteroid = TestAsteroid
        asteroidDao.insertAll(listOf(asteroid, asteroid, asteroid))
        assertEquals(asteroidDao.getRowCount(), 3)
    }


    @Test
    @Throws(Exception::class)
    fun getRowCount() = runBlocking {
        val asteroid1 = TestAsteroid
        asteroidDao.insert(asteroid1)
        asteroidDao.insert(asteroid1)
        asteroidDao.insert(asteroid1)
        assertEquals(asteroidDao.getRowCount(), 3)
    }

    @Test
    @Throws(Exception::class)
    fun clearDatabase() = runBlocking {
        val asteroid = TestAsteroid
        asteroidDao.insert(asteroid)
        asteroidDao.clear()
        assertEquals(asteroidDao.getRowCount(), 0)
    }

}

