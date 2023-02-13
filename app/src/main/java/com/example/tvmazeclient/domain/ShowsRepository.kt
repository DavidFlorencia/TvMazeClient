package com.example.tvmazeclient.domain

import androidx.lifecycle.LiveData
import com.example.tvmazeclient.data.db.LocalCast
import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.model.ShowResponse
import com.example.tvmazeclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    suspend fun getShowsSchedule(country : String, date : String): Resource<ScheduleResponse>

    suspend fun getShowsByQuery(query: String): Resource<QueryResponse>

    suspend fun getShowById(id: String): Resource<ShowResponse>

    suspend fun getCastById(id: String): Resource<CastResponse>

    suspend fun saveShow(show: LocalShow, cast: List<LocalCast>)

    suspend fun deleteShow(show: LocalShow)

    fun getSavedShows(): Flow<List<LocalShow>>

    fun getLocalShowById(id: String): Flow<LocalShow>

    fun getLocalCastById(id: String): Flow<List<LocalCast>>
}