package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.domain.ShowsRepository
import com.example.tvmazeclient.domain.usecase.GetCastByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * modulo para inyeccion de dependencias de los casos de uso
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetShowsScheduleUseCase(showsRepository: ShowsRepository): GetShowsScheduleUseCase{
        return GetShowsScheduleUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideGetShowsByQueryUseCase(showsRepository: ShowsRepository): GetShowsByQueryUseCase{
        return GetShowsByQueryUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideGetShowByIdUseCase(showsRepository: ShowsRepository): GetShowByIdUseCase{
        return GetShowByIdUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideGetCasyByIdUseCase(showsRepository: ShowsRepository): GetCastByIdUseCase{
        return GetCastByIdUseCase(showsRepository)
    }

}