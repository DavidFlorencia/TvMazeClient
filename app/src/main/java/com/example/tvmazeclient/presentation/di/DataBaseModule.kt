package com.example.tvmazeclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.tvmazeclient.data.db.LocalDao
import com.example.tvmazeclient.data.db.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): LocalDataBase {
        return Room.databaseBuilder(app, LocalDataBase::class.java, "shows_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(articleDatabase: LocalDataBase): LocalDao {
        return articleDatabase.getLocalDAO()
    }
}