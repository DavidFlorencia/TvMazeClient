package com.example.tvmazeclient.data.repository.dataSourceImpl

import com.example.tvmazeclient.data.api.ShowsApiService
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import retrofit2.Response

class ShowsRemoteDataSourceImpl(
    private val showsApiService: ShowsApiService
): ShowsRemoteDataSource {
    override suspend fun getCurrentShows(country: String, date: String): Response<ScheduleResponse> {
        return showsApiService.getSchedule(country, date)
    }

    override suspend fun getShowsByQuery(query: String): Response<QueryResponse> {
        return showsApiService.getShowsByQuery(query)
    }
}