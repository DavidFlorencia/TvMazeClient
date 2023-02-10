package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository

/**
 * caso de uso para solicitar lista inicial de shows se debe de proporcionar una fecha
 */
class GetShowsScheduleUseCase(private val showsRepository: ShowsRepository) {
    suspend fun execute(country: String, date: String): Resource<ScheduleResponse> {
        return showsRepository.getShowsSchedule(country, date)
    }
}