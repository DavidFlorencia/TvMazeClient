package com.example.tvmazeclient.presentation.di

import android.app.Application
import com.example.tvmazeclient.domain.usecase.*
import com.example.tvmazeclient.presentation.Utils
import com.example.tvmazeclient.presentation.viewmodel.DetailSavedViewModelFactory
import com.example.tvmazeclient.presentation.viewmodel.DetailViewModelFactory
import com.example.tvmazeclient.presentation.viewmodel.LandingViewModelFactory
import com.example.tvmazeclient.presentation.viewmodel.SavedViewModelFactory
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
    fun provideUtils(): Utils{
        return Utils()
    }

    @Singleton
    @Provides
    fun provideLandingViewModelFactory(app: Application,
                                       getShowsScheduleUseCase: GetShowsScheduleUseCase,
                                       getShowsByQueryUseCase: GetShowsByQueryUseCase,
                                       utils: Utils
    ): LandingViewModelFactory{
        return LandingViewModelFactory(
            app,
            getShowsScheduleUseCase,
            getShowsByQueryUseCase,
            utils
        )
    }

    @Singleton
    @Provides
    fun provideDetailViewModelFactory(
        app: Application,
        getShowByIdUseCas: GetShowByIdUseCase,
        getCastByIdUseCase: GetCastByIdUseCase,
        saveShowUseCase: SaveShowUseCase,
        utils: Utils
    ): DetailViewModelFactory{
        return DetailViewModelFactory(
            app,
            getShowByIdUseCas,
            getCastByIdUseCase,
            saveShowUseCase,
            utils
        )
    }

    @Singleton
    @Provides
    fun provideSavedViewModelFactory(
        getSavedShowUseCase: GetSavedShowsUseCase
    ): SavedViewModelFactory {
        return SavedViewModelFactory(
            getSavedShowUseCase
        )
    }

    @Singleton
    @Provides
    fun provideDetailSavedViewModelFactory(
        getSavedShowByIdUseCase: GetSavedShowByIdUseCase,
        getSavedCastByIdUseCase: GetSavedCastByIdUseCase,
        deleteShowUseCase: DeleteShowUseCase
    ): DetailSavedViewModelFactory {
        return DetailSavedViewModelFactory(
            getSavedShowByIdUseCase,
            getSavedCastByIdUseCase,
            deleteShowUseCase
        )
    }
}