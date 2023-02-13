package com.example.tvmazeclient.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cast")
data class LocalCast (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("cast_id")
    var castId: Long = 0L,
    @SerializedName("show_id")
    val showId: String = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("image")
    val image: String? = "",
)