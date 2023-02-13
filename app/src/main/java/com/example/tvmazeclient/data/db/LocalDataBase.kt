package com.example.tvmazeclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalShow::class, LocalCast::class],
    version = 1,
    exportSchema = false
)
abstract  class LocalDataBase : RoomDatabase(){
    abstract fun getLocalDAO(): LocalDao
}
