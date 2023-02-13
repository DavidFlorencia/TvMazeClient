package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.data.repository.ShowsRepositoryImpl
import com.example.tvmazeclient.data.repository.dataSource.ShowsLocalDataSource
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import com.example.tvmazeclient.domain.ShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * modulo para inyeccion de dependencias del repositorio
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideShowsRepository(
        showsRemoteDataSource: ShowsRemoteDataSource,
        showsLocalDataSource: ShowsLocalDataSource
    ): ShowsRepository{
        return ShowsRepositoryImpl(
            showsRemoteDataSource,
            showsLocalDataSource
        )
    }
}