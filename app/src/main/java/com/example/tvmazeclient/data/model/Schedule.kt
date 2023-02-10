package com.example.tvmazeclient.data.model


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("days")
    val days: List<String>,
    @SerializedName("time")
    val time: String
)