package com.example.tvmazeclient.presentation.viewmodel

import androidx.lifecycle.*
import com.example.tvmazeclient.domain.usecase.DeleteShowUseCase
import com.example.tvmazeclient.domain.usecase.GetSavedCastByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetSavedShowByIdUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailSavedViewModel(
    private val getSavedShowByIdUseCase: GetSavedShowByIdUseCase,
    private val getSavedCastByIdUseCase: GetSavedCastByIdUseCase,
    private val deleteShowUseCase: DeleteShowUseCase
) : ViewModel() {

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean>
        get() = _deleted

    fun deleteShow(id: String) = viewModelScope.launch {
        getShowById(id).value?.let { deleteShowUseCase.execute(it) }
        _deleted.postValue(true)
    }

    fun getShowById(id: String) = liveData {
        getSavedShowByIdUseCase.execute(id).collectLatest {
            emit(it)
        }
    }

    fun getCastById(id: String) = liveData {
        getSavedCastByIdUseCase.execute(id).collectLatest {
            emit(it)
        }
    }
}