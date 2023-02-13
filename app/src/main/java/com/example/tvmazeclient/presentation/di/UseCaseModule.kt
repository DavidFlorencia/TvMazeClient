package com.example.tvmazeclient.presentation.di

import com.example.tvmazeclient.domain.ShowsRepository
import com.example.tvmazeclient.domain.usecase.*
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

    @Singleton
    @Provides
    fun provideDeleteShowUseCase(showsRepository: ShowsRepository): DeleteShowUseCase {
        return DeleteShowUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedCastByIdUseCase(showsRepository: ShowsRepository): GetSavedCastByIdUseCase {
        return GetSavedCastByIdUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedShowByIdUseCase(showsRepository: ShowsRepository): GetSavedShowByIdUseCase {
        return GetSavedShowByIdUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedShowsUseCase(showsRepository: ShowsRepository): GetSavedShowsUseCase {
        return GetSavedShowsUseCase(showsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveShowUseCase(showsRepository: ShowsRepository): SaveShowUseCase {
        return SaveShowUseCase(showsRepository)
    }
}