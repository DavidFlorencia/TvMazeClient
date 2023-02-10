package com.example.tvmazeclient.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponseItem(
/*    @SerializedName("airdate")
    val airdate: String,
    @SerializedName("airstamp")
    val airstamp: String,
    @SerializedName("airtime")
    val airtime: String,*/
    @SerializedName("id")
    @Expose
    val id: Int,
/*    @SerializedName("image")
    val image: Image,
    @SerializedName("_links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("season")
    val season: Int,
    @SerializedName("show")
    val show: ShowX,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String*/
)