package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.data.db.LocalDao
import com.example.tvmazeclient.data.repository.dataSource.ShowsLocalDataSource
import com.example.tvmazeclient.data.repository.dataSourceImpl.ShowsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(localDao: LocalDao):ShowsLocalDataSource{
      return ShowsLocalDataSourceImpl(localDao)
    }
}













