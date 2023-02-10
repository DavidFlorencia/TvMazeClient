package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.domain.ShowsRepository
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

}