package com.example.tvmazeclient.data.repository.dataSource

import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.model.ShowResponse
import retrofit2.Response

interface ShowsRemoteDataSource {
    suspend fun getCurrentShows(country : String, date : String): Response<ScheduleResponse>
    suspend fun getShowsByQuery(query: String): Response<QueryResponse>
    suspend fun getShowById(id: String): Response<ShowResponse>
    suspend fun getCastById(id: String): Response<CastResponse>
}