package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.domain.ShowsRepository

/**
 * caso de uso para solicitar shows guardados
 */
class GetSavedShowsUseCase(private val showsRepository: ShowsRepository) {
    fun execute() = showsRepository.getSavedShows()
}