package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvmazeclient.domain.usecase.GetSavedShowsUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.Utils

/**
 * factory para landingViewModel
 */
class SavedViewModelFactory(
    private val getSavedShowsUseCase: GetSavedShowsUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedViewModel(
            getSavedShowsUseCase
        ) as T
    }
}