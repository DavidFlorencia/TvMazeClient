package com.example.tvmazeclient.data.model


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("nextepisode")
    val nextepisode: Nextepisode,
    @SerializedName("previousepisode")
    val previousepisode: Previousepisode,
    @SerializedName("self")
    val self: Self
)