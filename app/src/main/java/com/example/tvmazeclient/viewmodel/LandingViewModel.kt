package com.example.tvmazeclient.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
}