package com.example.tvmazeclient.domain.usecase

import com.example.tvmazeclient.data.db.LocalCast
import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.domain.ShowsRepository


class SaveShowUseCase(private val showsRepository: ShowsRepository) {
  suspend fun execute(show: LocalShow, cast: List<LocalCast>){
    showsRepository.saveShow(show, cast)
  }
}