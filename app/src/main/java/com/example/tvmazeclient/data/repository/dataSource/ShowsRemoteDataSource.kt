package com.example.tvmazeclient.data.repository.dataSource

import com.example.tvmazeclient.data.model.ApiResponse
import retrofit2.Response

interface ShowsRemoteDataSource {
    suspend fun getCurrentShows(country : String, date : String): Response<ApiResponse>
}