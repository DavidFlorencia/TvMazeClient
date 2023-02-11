package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.ShowResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.usecase.GetCastByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowByIdUseCase
import com.example.tvmazeclient.presentation.isNetworkAvailable
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(
    private val app: Application,
    private val getShowByIdUseCase: GetShowByIdUseCase,
    private val getCastByIdUseCase: GetCastByIdUseCase,
) : AndroidViewModel(app){
    /**
     * liva data que contiene la información del show consultado
     */
    private val _infoShow = MutableLiveData<Resource<ShowResponse>>()
    val infoShow: LiveData<Resource<ShowResponse>>
        get() = _infoShow

    /**
     * función que invoca el caso de uso para buscar show por id
     */
    fun getShowById(id: String) = viewModelScope.launch {
        _infoShow.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getShowByIdUseCase.execute(
                    id
                )
                _infoShow.postValue(response)
            } else {
                _infoShow.postValue(Resource.Error("No internet connection"))
            }
        }catch(e: Exception){
            _infoShow.postValue(Resource.Error(e.message.toString()))
        }
    }

    /**
     * liva data que contiene la información del cast por id
     */
    private val _cast = MutableLiveData<Resource<CastResponse>>()
    val cast: LiveData<Resource<CastResponse>>
        get() = _cast

    /**
     * función que invoca el caso de uso para buscar cast por id
     */
    fun getCastById(id: String) = viewModelScope.launch {
        _cast.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getCastByIdUseCase.execute(
                    id
                )
                _cast.postValue(response)
            } else {
                _cast.postValue(Resource.Error("No internet connection"))
            }
        }catch(e: Exception){
            _cast.postValue(Resource.Error(e.message.toString()))
        }
    }
}