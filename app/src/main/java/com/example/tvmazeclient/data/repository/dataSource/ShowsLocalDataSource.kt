package com.example.tvmazeclient.data.repository.dataSource

import androidx.lifecycle.LiveData
import com.example.tvmazeclient.data.db.LocalCast
import com.example.tvmazeclient.data.db.LocalShow
import kotlinx.coroutines.flow.Flow

interface ShowsLocalDataSource {
    suspend fun saveShow(show: LocalShow, cast: List<LocalCast>)
    suspend fun deleteShow(show: LocalShow)
    fun getSavedShows(): Flow<List<LocalShow>>
    fun getShowById(id: String): Flow<LocalShow>
    fun getCastById(id: String): Flow<List<LocalCast>>
}