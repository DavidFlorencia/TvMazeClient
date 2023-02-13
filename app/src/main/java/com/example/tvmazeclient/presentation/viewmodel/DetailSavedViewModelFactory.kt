package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvmazeclient.domain.usecase.*
import com.example.tvmazeclient.presentation.Utils

class DetailSavedViewModelFactory(
    private val getSavedShowByIdUseCase: GetSavedShowByIdUseCase,
    private val getSavedCastByIdUseCase: GetSavedCastByIdUseCase,
    private val deleteShowUseCase: DeleteShowUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailSavedViewModel(
            getSavedShowByIdUseCase,
            getSavedCastByIdUseCase,
            deleteShowUseCase
        ) as T
    }
}