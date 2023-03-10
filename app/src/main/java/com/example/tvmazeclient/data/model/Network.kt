package com.example.tvmazeclient.data.model

import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("officialSite")
    val officialSite: String
)