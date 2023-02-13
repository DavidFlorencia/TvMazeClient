package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.Utils
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class LandingViewModelFactoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var app: Application
    private lateinit var getShowsScheduleUseCase: GetShowsScheduleUseCase
    private lateinit var getShowsByQueryUseCase: GetShowsByQueryUseCase
    private lateinit var utils: Utils

    private lateinit var factory: LandingViewModelFactory

    @Before
    fun setUp() {
        app = Mockito.mock(Application::class.java)
        getShowsScheduleUseCase = Mockito.mock(GetShowsScheduleUseCase::class.java)
        getShowsByQueryUseCase = Mockito.mock(GetShowsByQueryUseCase::class.java)
        utils = Mockito.mock(Utils::class.java)
    }

    @Test
    fun `validate return type from LandingViewModelFactoryTest_create`() {
        factory = LandingViewModelFactory(
            app,
            getShowsScheduleUseCase,
            getShowsByQueryUseCase,
            utils
        )

        val valueReturned = factory.create(LandingViewModel::class.java)
        Truth.assertThat(valueReturned).isInstanceOf(LandingViewModel::class.java)
    }
}