package com.example.tvmazeclient.presentation.di

import android.app.Application
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}