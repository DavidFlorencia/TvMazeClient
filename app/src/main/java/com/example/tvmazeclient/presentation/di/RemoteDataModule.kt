package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.data.api.ShowsApiService
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import com.example.tvmazeclient.data.repository.dataSourceImpl.ShowsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * modulo para inyeccion de dependencias de retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideShowsRemoteDataSource(showsApiService: ShowsApiService):ShowsRemoteDataSource{
        return ShowsRemoteDataSourceImpl(showsApiService)
    }
}