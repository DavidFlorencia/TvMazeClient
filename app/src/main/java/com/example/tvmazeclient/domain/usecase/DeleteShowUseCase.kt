package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.domain.ShowsRepository


class DeleteShowUseCase(private val showsRepository: ShowsRepository) {
  suspend fun execute(show: LocalShow)=showsRepository.deleteShow(show)
}