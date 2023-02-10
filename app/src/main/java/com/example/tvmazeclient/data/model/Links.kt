package com.example.tvmazeclient.data.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: Self,
    @SerializedName("show")
    val show: Show
)