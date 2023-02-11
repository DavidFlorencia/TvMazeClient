package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.data.model.ShowResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository

/**
 * caso de uso para buscar la información de un show por su id
 */
class GetShowByIdUseCase(private val showsRepository: ShowsRepository) {
    suspend fun execute(id: String): Resource<ShowResponse>{
        return showsRepository.getShowById(id)
    }
}