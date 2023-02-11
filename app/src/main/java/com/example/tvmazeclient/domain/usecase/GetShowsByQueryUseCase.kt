package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository

/**
 * caso de uso para solicitar lista inicial de shows se debe de proporcionar una fecha
 */
class GetShowsByQueryUseCase(private val showsRepository: ShowsRepository) {
    suspend fun execute(query: String): Resource<QueryResponse> {
        return showsRepository.getShowsByQuery(query)
    }
}