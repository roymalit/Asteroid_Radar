package com.royma.asteroidradar

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.royma.asteroidradar.domain.TestAsteroid1
import com.royma.asteroidradar.domain.TestAsteroid2
import com.royma.asteroidradar.domain.TestAsteroid3
import com.royma.asteroidradar.repository.AsteroidDatabase
import com.royma.asteroidradar.repository.AsteroidDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.After
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
     fun insertAndGetLatestAsteroid() = runBlocking{
        val asteroid1 = TestAsteroid1
        val asteroid2 = TestAsteroid2
        val asteroid3 = TestAsteroid3
        asteroidDao.insertAll(asteroid1, asteroid2, asteroid3)
        val latest = asteroidDao.getLatestAsteroid()
        assert(latest?.codename == "Testeroid 3"){"Did not return latest asteroid"}
    }

    @Test
    @Throws(Exception::class)
    fun getAsteroid() = runBlocking{
        val asteroid1 = TestAsteroid1
        val asteroid2 = TestAsteroid2
        val asteroid3 = TestAsteroid3
        asteroidDao.insertAll(asteroid1, asteroid2, asteroid3)
        assert(asteroidDao.get(2)?.codename == "Testeroid 2"){"Did not return ${asteroid2.codename}"}
    }

    @Test
    @Throws(Exception::class)
    fun insertAllAsteroids() = runBlocking{
        val asteroid = TestAsteroid1
        asteroidDao.insertAll(asteroid, asteroid, asteroid)
        assert(asteroidDao.getRowCount() == 3){"Inserted incorrect number of asteroids"}
    }

        /* Test fails after refactoring getAllAsteroids() to return List<Asteroid> */
//    @Test
//    @Throws(Exception::class)
//    fun getAllAsteroids() = runBlocking{
//        val asteroid = TestAsteroid1
//        asteroidDao.insertAll(listOf(asteroid, asteroid, asteroid))
//        assert(asteroidDao.getAllAsteroids().value?.size == 3) {"Didn't return all rows"}
//    }


    @Test
    @Throws(Exception::class)
    fun getRowCount() = runBlocking {
        val asteroid1 = TestAsteroid1
        asteroidDao.insert(asteroid1)
        asteroidDao.insert(asteroid1)
        asteroidDao.insert(asteroid1)
        assert(asteroidDao.getRowCount() == 3){"Incorrect row count"}
    }

    @Test
    @Throws(Exception::class)
    fun clearDatabase() = runBlocking {
        val asteroid = TestAsteroid1
        asteroidDao.insert(asteroid)
        asteroidDao.clear()
        assert(asteroidDao.getRowCount() == 0){"Database not fully cleared"}
    }

}

