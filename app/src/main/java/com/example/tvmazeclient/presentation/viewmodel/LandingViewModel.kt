package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.isNetworkAvailable
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class LandingViewModel(
    private val app: Application,
    private val getShowsScheduleUseCase: GetShowsScheduleUseCase,
    private val getShowsByQueryUseCase: GetShowsByQueryUseCase,
) : AndroidViewModel(app){
    /**
     * variables para lógica de consumo en pantalla inicial
     * se utilza "_variable" para manejar el encapsulamiento de datos
     * "variable" solo esta visible para otras clases, pero no se puede alterar
     */
    private val _dateIso8601 = MutableLiveData<String>()
    val dateIso8601: LiveData<String>
        get() = _dateIso8601

    /**
     * inicialización de liveData de fecha actual
     */
    init {
        _dateIso8601.value = SimpleDateFormat("yyyy-MM-dd")
            .format(Date())
    }

    /**
     * @return string que contiene fecha en el formato solicitado
     * para el titulo de la pantalla principal
     */
    fun getStringDate(): String = SimpleDateFormat(
                "EEEE dd 'de' MMMM yyyy",
                Locale("es", "ES")
            ).format(Date())

    /**
     * liva data que contiene lista de shows del día
     */
    private val _currentShows = MutableLiveData<Resource<ScheduleResponse>>()
    val currentShows: LiveData<Resource<ScheduleResponse>>
        get() = _currentShows

    /**
     * función que invoca el caso de uso para obtener lista de shows
     */
    fun getShowsSchedule(currentDate: String) = viewModelScope.launch {
        _currentShows.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getShowsScheduleUseCase.execute(
                    "US",
                    currentDate
                )
                _currentShows.postValue(response)
            } else {
                _currentShows.postValue(Resource.Error("No internet connection"))
            }
        }catch(e: Exception){
            _currentShows.postValue(Resource.Error(e.message.toString()))
        }
    }

    /**
     * liva data que contiene lista de shows buscados
     */
    private val _queryShows = MutableLiveData<Resource<QueryResponse>>()
    val queryShows: LiveData<Resource<QueryResponse>>
        get() = _queryShows

    /**
     * función que invoca el caso de uso para buscar shows por query
     */
    fun getShowsByQuery(query: String) = viewModelScope.launch {
        _queryShows.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getShowsByQueryUseCase.execute(
                    query
                )
                _queryShows.postValue(response)
            } else {
                _queryShows.postValue(Resource.Error("No internet connection"))
            }
        }catch(e: Exception){
            _queryShows.postValue(Resource.Error(e.message.toString()))
        }
    }
}