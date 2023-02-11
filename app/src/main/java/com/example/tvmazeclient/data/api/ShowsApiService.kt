package com.example.tvmazeclient.data.api

import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.model.ShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("search/shows")
    suspend fun getShowsByQuery(
        @Query("q") query: String
    ): Response<QueryResponse>

    @GET("shows/{id}")
    suspend fun getShowById(
        @Path("id") id: String
    ): Response<ShowResponse>

    @GET("shows/{id}/cast")
    suspend fun getCastById(
        @Path("id") id: String
    ): Response<CastResponse>
}