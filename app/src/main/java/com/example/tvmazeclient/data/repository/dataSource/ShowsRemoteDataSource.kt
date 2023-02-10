package com.example.tvmazeclient.data.repository.dataSource

import com.example.tvmazeclient.data.model.ScheduleResponse
import retrofit2.Response

interface ShowsRemoteDataSource {
    suspend fun getCurrentShows(country : String, date : String): Response<ScheduleResponse>
}