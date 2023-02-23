package com.royma.asteroidradar.api

import com.royma.asteroidradar.Constants
import com.royma.asteroidradar.PictureOfDay
import com.royma.asteroidradar.PrivateConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        // Scalars needed to read & convert JSON OBJECT
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface NasaApiService {
    /**
     * GETs 'picture of the day' from NASA api
     * @param key Personal API key needed to access NASA server data
     */
    @GET("/planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") key: String = PrivateConstants.MY_API_KEY
    ): PictureOfDay

    /**
     * GETs asteroid data from NASA api.
     * @param startDate The date from which we'll retrieve asteroid data
     * @param apiKey Personal API key needed to access NASA server data
     */
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,
            Locale.getDefault()).format(Date()),
        @Query("api_key") apiKey: String = PrivateConstants.MY_API_KEY,
    ): String

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NasaApi{
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}