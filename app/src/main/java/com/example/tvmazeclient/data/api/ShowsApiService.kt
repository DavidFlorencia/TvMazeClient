package com.example.tvmazeclient.data.api

import com.example.tvmazeclient.data.model.ScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * interface que declara metodos get para consumo de servicios web
 */
interface ShowsApiService {

    @GET("schedule")
    suspend fun getSchedule(
        @Query("country") country: String = "US",
        @Query("date") date: String
    ): Response<ScheduleResponse>

}