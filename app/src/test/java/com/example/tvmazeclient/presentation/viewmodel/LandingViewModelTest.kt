package com.example.tvmazeclient.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tvmazeclient.MainDispatcherRule
import com.example.tvmazeclient.data.api.ShowsApiService
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.usecase.GetShowsByQueryUseCase
import com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase
import com.example.tvmazeclient.presentation.Utils
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException

class LandingViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var service: ShowsApiService
    private lateinit var server: MockWebServer

    private lateinit var app: Application
    private lateinit var getShowsScheduleUseCase: GetShowsScheduleUseCase
    private lateinit var getShowsByQueryUseCase: GetShowsByQueryUseCase
    private lateinit var utils: Utils

    private lateinit var viewModel: LandingViewModel

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShowsApiService::class.java)

        app = Mockito.mock(Application::class.java)
        getShowsScheduleUseCase = Mockito.mock(GetShowsScheduleUseCase::class.java)
        getShowsByQueryUseCase = Mockito.mock(GetShowsByQueryUseCase::class.java)
        utils = Mockito.mock(Utils::class.java)
    }

    @Test
    fun `when dateIso8601 is initialized utils_currentDateWithIso8601Format is called 1 times`() {
        val date = "2023-02-12"
        Mockito.`when`(utils.currentDateWithIso8601Format())
            .thenReturn(date)

        viewModel = LandingViewModel(
            app,
            getShowsScheduleUseCase,
            getShowsByQueryUseCase,
            utils
        )

        Mockito.verify(utils, Mockito.times(1))
            .currentDateWithIso8601Format()
    }

    @Test
    fun `dateIso8601 is initialized succes`() {
        val date = "2023-02-12"
        Mockito.`when`(utils.currentDateWithIso8601Format())
            .thenReturn(date)

        viewModel = LandingViewModel(
            app,
            getShowsScheduleUseCase,
            getShowsByQueryUseCase,
            utils
        )

        Truth.assertThat(viewModel.dateIso8601.value).isEqualTo(date)
    }

    @Test
    fun `when getStringDate is called utils_getStringDate is called 1 times`() {
        val date = "domingo 12 de febrero 2023"
        Mockito.`when`(utils.getStringDate())
            .thenReturn(date)

        viewModel = LandingViewModel(
            app,
            getShowsScheduleUseCase,
            getShowsByQueryUseCase,
            utils
        )

        val valueReturned = viewModel.getStringDate()

        Mockito.verify(utils, Mockito.times(1))
            .getStringDate()
        /**
         * ¿se esta retornando el valor correcto?
         */
        Truth.assertThat(valueReturned).isEqualTo(date)
    }

    @Test
    fun `when getShowsSchedule is called with network available then currentShows is setted success`(){
        runBlocking {
            /**
             * inicializar variables de entrada
             */
            val country = "US"
            val date = "today"

            /**
             * preparar mocks con respuesta exitosa e internet disponible
             */
            enqueueMockResponse("scheduleResponse.json")
            val scheduleResource = responseToResource(service.getSchedule(country,date))
            Mockito.`when`(getShowsScheduleUseCase.execute(country, date))
                .thenReturn(scheduleResource)

            Mockito.`when`(utils.isNetworkAvailable(app)).thenReturn(true)

            viewModel = LandingViewModel(
                app,
                getShowsScheduleUseCase,
                getShowsByQueryUseCase,
                utils
            )

            viewModel.getShowsSchedule(date)

            Truth.assertThat(viewModel.currentShows.value).isEqualTo(scheduleResource)
        }
    }

    @Test
    fun `when getShowsSchedule is called with network not available then currentShows is setted as resource error`(){
        runBlocking {
            /**
             * inicializar variables de entrada
             */
            val country = "US"
            val date = "today"

            /**
             * preparar mocks con respuesta exitosa e internet no disponible
             */
            enqueueMockResponse("scheduleResponse.json")
            val scheduleResource = responseToResource(service.getSchedule(country,date))
            Mockito.`when`(getShowsScheduleUseCase.execute(country, date))
                .thenReturn(scheduleResource)

            Mockito.`when`(utils.isNetworkAvailable(app)).thenReturn(false)

            viewModel = LandingViewModel(
                app,
                getShowsScheduleUseCase,
                getShowsByQueryUseCase,
                utils
            )

            viewModel.getShowsSchedule(date)

            Truth.assertThat(viewModel.currentShows.value).isInstanceOf(Resource.Error::class.java)
            Truth.assertThat(viewModel.currentShows.value?.message)
                .isEqualTo("No internet connection")
        }
    }

    @Test
    fun `when getShowsSchedule respond with a exception then currentShows is setted as resource error`(){
        runBlocking {
            /**
             * inicializar variables de entrada
             */
            val country = "US"
            val date = "today"
            val exception = RuntimeException("unknown exception")

            /**
             * preparar mocks con excepcion
             */
            Mockito.`when`(getShowsScheduleUseCase.execute(country, date))
                .thenThrow(exception)

            Mockito.`when`(utils.isNetworkAvailable(app))
                .thenReturn(true)

            viewModel = LandingViewModel(
                app,
                getShowsScheduleUseCase,
                getShowsByQueryUseCase,
                utils
            )

            viewModel.getShowsSchedule(date)

            Truth.assertThat(viewModel.currentShows.value).isInstanceOf(Resource.Error::class.java)
            Truth.assertThat(viewModel.currentShows.value?.message)
                .isEqualTo(exception.message)
        }
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    private fun <T> responseToResource(response: Response<T>): Resource<T> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}