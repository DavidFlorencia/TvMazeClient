package com.example.tvmazeclient.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tvmazeclient.data.api.ShowsApiService
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository
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

class UseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ShowsApiService
    private lateinit var server: MockWebServer
    private lateinit var repository: ShowsRepository

    private lateinit var getShowsUseCase:GetShowsScheduleUseCase

    @Before
    fun setUp(){
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShowsApiService::class.java)

        repository = Mockito.mock(ShowsRepository::class.java)
    }


    /**
     * test que valida el resultado de la ejecución del caso de uso
     * [com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase]
     * cuando la respuesta del servidor es exitosa
     */
    @Test
    fun getShowsSchedule_whenResponseIsSucces_correctResults(){
        runBlocking {
            /**
             * inicializar variables de entrada
             */
            val country = "US"
            val date = "today"

            /**
             * preparar mocks con respuesta exitosa
             */
            enqueueMockResponse("scheduleResponse.json")
            val resource = responseToResource(service.getSchedule(country,date))
            Mockito.`when`(repository.getShowsSchedule(country, date))
                .thenReturn(resource)

            /**
             * instanciar objeto a analizar
             */
            getShowsUseCase = GetShowsScheduleUseCase(repository)

            /**
             * ejecutar metodo a analizar
             */
            val valueReturned = getShowsUseCase.execute(country, date)

            /**
             * validar resultados
             */
            // ¿repository.getShowsSchedule se ejecuta 1 vez?
            Mockito.verify(repository, Mockito.times(1))
                .getShowsSchedule(country, date)
            // ¿el valor retornado es nulo?
            Truth.assertThat(valueReturned).isNotNull()
            // ¿el valor retornado es una instancia de Resource.Succes?
            Truth.assertThat(valueReturned).isInstanceOf(Resource.Success::class.java)
            // ¿el tamaño de la lista esperada es correto?
            Truth.assertThat(valueReturned.data?.size).isEqualTo(71)
            // ¿el contenido es el esperado?
            valueReturned.data?.get(0)?.apply {
                Truth.assertThat(id).isEqualTo(2449357)
                Truth.assertThat(airdate).isEqualTo("2022-12-01")
                Truth.assertThat(airtime).isEqualTo("00:00")
                Truth.assertThat(showInfo.id).isEqualTo(59312)
                Truth.assertThat(showInfo.image?.medium)
                    .isEqualTo("https://static.tvmaze.com/uploads/images/medium_portrait/441/1104117.jpg")
                Truth.assertThat(showInfo.network?.officialSite).isEqualTo("https://www.sho.com/")
            }
        }
    }

    /**
     * test que valida el resultado de la ejecución del caso de uso
     * [com.example.tvmazeclient.domain.usecase.GetShowsScheduleUseCase]
     * cuando la respuesta del servidor es erronea
     */
    @Test
    fun getShowsSchedule_whenResponseIsError_correctResults(){
        runBlocking {
            /**
             * inicializar variables de entrada
             */
            val country = "US"
            val date = "today"

            /**
             * preparar mocks con respuesta erronea
             */
            server.enqueue(
                MockResponse().setResponseCode(500)
                    .setHeader("content-type", "application/json")
                    .setBody("{}")
            )
            val resource = responseToResource(service.getSchedule(country,date))
            Mockito.`when`(repository.getShowsSchedule(country, date))
                .thenReturn(resource)

            /**
             * instanciar objeto a analizar
             */
            getShowsUseCase = GetShowsScheduleUseCase(repository)

            /**
             * ejecutar metodo a analizar
             */
            val valueReturned = getShowsUseCase.execute(country, date)

            /**
             * validar resultados
             */
            // ¿repository.getShowsSchedule se ejecuta 1 vez?
            Mockito.verify(repository, Mockito.times(1))
                .getShowsSchedule(country, date)
            // ¿el valor retornado es nulo?
            Truth.assertThat(valueReturned).isNotNull()
            // ¿el valor retornado es una instancia de Resource.Error?
            Truth.assertThat(valueReturned).isInstanceOf(Resource.Error::class.java)
            // ¿contiene un mensaje de error?
            Truth.assertThat(valueReturned.message).isNotEmpty()
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

    private fun <T> responseToResource(response: Response<T>):Resource<T>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}