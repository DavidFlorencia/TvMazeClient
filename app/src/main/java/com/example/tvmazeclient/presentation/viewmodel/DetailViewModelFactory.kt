package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvmazeclient.domain.usecase.GetCastByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowByIdUseCase

/**
 * factory para detailViewModel
 */
class DetailViewModelFactory(
    private val app: Application,
    private val getShowByIdUseCase: GetShowByIdUseCase,
    private val getCastByIdUseCase: GetCastByIdUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(app, getShowByIdUseCase, getCastByIdUseCase) as T
    }
}