package com.example.tvmazeclient.data.model


import com.google.gson.annotations.SerializedName

data class ShowResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: Network?,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("officialSite")
    val officialSite: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("schedule")
    val schedule: Schedule,
){
    data class Rating(
        @SerializedName("average")
        val average: String
    )
}