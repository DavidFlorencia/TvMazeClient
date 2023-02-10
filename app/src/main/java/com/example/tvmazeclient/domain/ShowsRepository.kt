package com.example.tvmazeclient.domain

import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.util.Resource

interface ShowsRepository {
    suspend fun getShowsSchedule(country : String, date : String): Resource<ScheduleResponse>
}