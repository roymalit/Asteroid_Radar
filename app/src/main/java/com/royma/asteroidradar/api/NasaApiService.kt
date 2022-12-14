package com.royma.asteroidradar.api

import com.royma.asteroidradar.Constants
import com.royma.asteroidradar.PrivateConstants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface NasaApiService {

//    @GET("/planetary/apod")
//    suspend fun getPictureOfDay(
//        @Query("api_key") key: String = PrivateConstants.MY_API_KEY
//    ): PictureOfDay
//
//    @GET("neo/rest/v1/feed")
//    suspend fun getAsteroids(
//        @Query("start_date") startDate: String = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,
//                        Locale.getDefault()).format(Date()),
//        @Query("api_key") apiKey: String = PrivateConstants.MY_API_KEY,
//    ): ResponseBody

    @GET("/planetary/apod")
    fun getPictureOfDay(
        @Query("api_key") key: String = PrivateConstants.MY_API_KEY
    ): Call<String>

    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT,
            Locale.getDefault()).format(Date()),
        @Query("api_key") apiKey: String = PrivateConstants.MY_API_KEY,
    ): Call<String>

}

object NasaApi{
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}