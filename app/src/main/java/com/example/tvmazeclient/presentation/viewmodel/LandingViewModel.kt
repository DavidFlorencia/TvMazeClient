package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.example.tvmazeclient.data.model.ApiResponse
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class LandingViewModel(
    private val app: Application,
    private val getShowsScheduleUseCase: GetShowsScheduleUseCase
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
     * liva data que contiene lista de shows
     */
    private val _currentShows = MutableLiveData<Resource<ApiResponse>>()
    val currentShows: LiveData<Resource<ApiResponse>>
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
     * función que valida si estamos conectados a internet
     */
    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}