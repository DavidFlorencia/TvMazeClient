package com.example.tvmazeclient.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

class Utils{
    /**
     * función que valida si estamos conectados a internet
     */
    fun isNetworkAvailable(context: Context?):Boolean{
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

    /**
     * @return String que contiene fecha del dispositivo en
     * formato iso8601
     */
    fun currentDateWithIso8601Format(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.US)
            .format(Date())

    /**
     * @return String que contiene fecha del dispositivo en
     * español en formato personalizado
     */
    fun getStringDate(): String = SimpleDateFormat(
        "EEEE dd 'de' MMMM yyyy",
        Locale("es", "ES")
    ).format(Date())
}