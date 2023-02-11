package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase

class LandingViewModelFactory(
    private val app: Application,
    private val getShowsScheduleUseCase: GetShowsScheduleUseCase,
    private val getShowsByQueryUseCase: GetShowsByQueryUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LandingViewModel(app, getShowsScheduleUseCase, getShowsByQueryUseCase) as T
    }
}