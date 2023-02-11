package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository

/**
 * caso de uso para buscar el cast de un show por su id
 */
class GetCastByIdUseCase(private val showsRepository: ShowsRepository) {
    suspend fun execute(id: String): Resource<CastResponse> {
        return showsRepository.getCastById(id)
    }
}