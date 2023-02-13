package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.domain.ShowsRepository

/**
 * caso de uso para solicitar por id show guardado
 */
class GetSavedShowByIdUseCase(private val showsRepository: ShowsRepository) {
    fun execute(id: String) = showsRepository.getLocalShowById(id)
}