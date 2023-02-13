package com.example.tvmazeclient.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "shows")
data class LocalShow (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("show_id")
    val showId: String,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("network")
    val network: String? = "",
    @SerializedName("site")
    val site: String? = "",
    @SerializedName("resume")
    val resume: String? = "",
    @SerializedName("genres")
    val genres: String? = "",
    @SerializedName("time")
    val time: String? = "",
    @SerializedName("days")
    val days: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("rating")
    val rating: String? = ""
)