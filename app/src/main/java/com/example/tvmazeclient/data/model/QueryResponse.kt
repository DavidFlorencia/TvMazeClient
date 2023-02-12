package com.example.tvmazeclient.data.model


import com.google.gson.annotations.SerializedName

class QueryResponse : ArrayList<QueryResponse.ResponseItem>(){
    data class ResponseItem(
        @SerializedName("show")
        val show: Show
    )

    data class Show(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("network")
        val network: Network?,
        @SerializedName("schedule")
        val schedule: Schedule,
        @SerializedName("image")
        val image: Image?,
    )
}