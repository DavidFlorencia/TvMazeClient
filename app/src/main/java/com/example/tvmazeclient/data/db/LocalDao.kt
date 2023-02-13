package com.example.tvmazeclient.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(show: LocalShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCast(list: List<LocalCast>)

    @Delete
    suspend fun delete(show: LocalShow)

    @Query("SELECT * FROM shows")
    fun getSavedShows(): Flow<List<LocalShow>>

    @Query("SELECT * from shows WHERE showId = :id")
    fun getShowById(id: String): Flow<LocalShow>

    @Query("SELECT * FROM `cast` where showId = :id")
    fun getCastById(id: String): Flow<List<LocalCast>>
}