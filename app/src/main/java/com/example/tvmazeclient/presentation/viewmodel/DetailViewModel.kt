package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.tvmazeclient.data.db.LocalCast
import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.ShowResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.usecase.GetCastByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowByIdUseCase
import com.example.tvmazeclient.domain.usecase.SaveShowUseCase
import com.example.tvmazeclient.presentation.Utils
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(
    private val app: Application,
    private val getShowByIdUseCase: GetShowByIdUseCase,
    private val getCastByIdUseCase: GetCastByIdUseCase,
    private val saveShowUseCase: SaveShowUseCase,
    private val utils: Utils
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
            if (utils.isNetworkAvailable(app)) {
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
            if (utils.isNetworkAvailable(app)) {
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

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean>
        get() = _saved
    fun saveShow() = viewModelScope.launch {
        infoShow.value?.data?.apply {
            val site = officialSite ?: (network?.officialSite ?: "")
            val showId = id.toString()

            val localShow = LocalShow(
                showId = showId,
                name = name,
                network = network?.name,
                site = site,
                resume = summary,
                genres = genres.joinToString(", "),
                time = schedule.time,
                days = schedule.days.joinToString(", "),
                image = image?.medium,
                rating = rating.average
            )

            cast.value?.data.apply {
                val cast = this?.map { person ->
                    LocalCast(
                        showId = showId,
                        name = person.data.name,
                        image = person.data.image?.medium
                    )
                }
                saveShowUseCase.execute(localShow,cast ?: listOf())
                _saved.postValue(true)
            }

        }
    }
}