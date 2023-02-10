package com.example.tvmazeclient.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * string correspondiente a url base
 */
private const val BASE_URL =
    "https://api.tvmaze.com/"

/**
 * inicializacion de retrofit
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * definicion de metodos get para consumo de servicios web
 */
interface TvMazeApiService {
    @GET("schedule")
    fun getSchedule(
        @Query("country") country: String = "US",
        @Query("date") date: String
    ): Call<String>
}

/**
 * constructor de servicio utilizado para consumo de api
 */
object TvMazeApi {
    val retrofitService : TvMazeApiService by lazy {
        retrofit.create(TvMazeApiService::class.java)
    }
}