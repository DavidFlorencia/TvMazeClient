package com.example.tvmazeclient.presentation.di

import android.app.Application
import com.example.tvmazeclient.domain.usecase.GetCastByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowByIdUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.viewmodel.DetailViewModelFactory
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * modulo para inyeccion de dependencias de view model factory
 */
@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideLandingViewModelFactory(app: Application,
                                       getShowsScheduleUseCase: GetShowsScheduleUseCase,
                                       getShowsByQueryUseCase: GetShowsByQueryUseCase
    ): LandingViewModelFactory{
        return LandingViewModelFactory(app, getShowsScheduleUseCase,getShowsByQueryUseCase)
    }

    @Singleton
    @Provides
    fun provideDetailViewModelFactory(app: Application,
                                       getShowByIdUseCas: GetShowByIdUseCase,
                                       getCastByIdUseCase: GetCastByIdUseCase
    ): DetailViewModelFactory{
        return DetailViewModelFactory(app, getShowByIdUseCas,getCastByIdUseCase)
    }
}