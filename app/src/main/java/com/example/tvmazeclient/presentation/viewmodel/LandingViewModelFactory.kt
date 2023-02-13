package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.Utils

/**
 * factory para landingViewModel
 */
class LandingViewModelFactory(
    private val app: Application,
    private val getShowsScheduleUseCase: GetShowsScheduleUseCase,
    private val getShowsByQueryUseCase: GetShowsByQueryUseCase,
    private val utils: Utils
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LandingViewModel(
            app,
            getShowsScheduleUseCase,
            getShowsByQueryUseCase,
            utils
        ) as T
    }
}