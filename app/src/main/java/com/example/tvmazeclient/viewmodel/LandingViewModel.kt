package com.example.tvmazeclient.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvmazeclient.api.TvMazeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class LandingViewModel : ViewModel(){
    /**
     * variables para lógica de consumo en pantalla inicial
     */
    private val _dateIso8601 = MutableLiveData<String>()
    val dateIso8601: LiveData<String>
        get() = _dateIso8601

    /**
     * variables para temporales para validar consumo de servicio
     */
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

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

    fun getTvMazeSchedule(currentDate: String) {
        TvMazeApi.retrofitService.getSchedule(date = currentDate).enqueue(
            object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
    }
}