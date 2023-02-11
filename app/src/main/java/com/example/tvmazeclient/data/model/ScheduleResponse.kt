package com.example.tvmazeclient.data.model

import com.google.gson.annotations.SerializedName

/**
 * modelo de datos para parseo de respuesta al metodo getSchedule
 */
class ScheduleResponse : ArrayList<ScheduleResponse.Show>(){
    data class Show(
        @SerializedName("id")
        val id: Int,
        @SerializedName("show")
        val showInfo: ShowInfo,
        @SerializedName("airdate")
        val airdate: String,
        @SerializedName("airtime")
        val airtime: String,
    )

    data class ShowInfo(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("image")
        val image: Image?,
        @SerializedName("network")
        val network: Network?,
    )

    data class Network(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

    data class Image(
        @SerializedName("medium")
        val medium: String,
    )
}