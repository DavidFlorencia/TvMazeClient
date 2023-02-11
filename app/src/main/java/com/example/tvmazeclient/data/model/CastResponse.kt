package com.example.tvmazeclient.data.model


import com.google.gson.annotations.SerializedName

class CastResponse : ArrayList<CastResponse.Person>(){
    data class Person(
        @SerializedName("person")
        val data: Data
    )

    data class Data(
        @SerializedName("name")
        val name: String,
        @SerializedName("image")
        val image: Image
    )
}