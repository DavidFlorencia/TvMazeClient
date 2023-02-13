package com.example.tvmazeclient.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tvmazeclient.domain.usecase.GetSavedShowsUseCase
import kotlinx.coroutines.flow.collectLatest

class SavedViewModel(
    private val getSavedShowsUseCase: GetSavedShowsUseCase,
) : ViewModel() {

    fun getSavedShows() = liveData {
        getSavedShowsUseCase.execute().collectLatest {
            emit(it)
        }
    }

}