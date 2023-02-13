package com.example.tvmazeclient.data.repository

import com.example.tvmazeclient.data.api.ShowsApiService
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ShowsRepositoryTest {
    private lateinit var service: ShowsApiService
    private lateinit var server: MockWebServer
    private lateinit var dataSource: ShowsRemoteDataSource
    private lateinit var repository: ShowsRepository

    @Before
    fun setUp(){
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShowsApiService::class.java)

        dataSource = Mockito.mock(ShowsRemoteDataSource::class.java)
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

    /**
     * test que valida el resultado de la ejecución de
     * [com.example.tvmazeclient.data.repository.ShowsRepositoryImpl.getShowsSchedule]
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
            val responseBody = service.getSchedule(country,date)
            Mockito.`when`(dataSource.getCurrentShows(country, date)).thenReturn(responseBody)

            /**
             * instanciar objeto a analizar
             */
            repository = ShowsRepositoryImpl(dataSource)

            /**
             * ejecutar metodo a analizar
             */
            val valueReturned = repository.getShowsSchedule(country, date)

            /**
             * validar resultados
             */
            // ¿dataSource.getCurrentShows se ejecuta 1 vez?
            Mockito.verify(dataSource, Mockito.times(1))
                .getCurrentShows(country, date)
            // ¿el valor retornado es nulo?
            assertThat(valueReturned).isNotNull()
            // ¿el valor retornado es una instancia de Resource.Succes?
            assertThat(valueReturned).isInstanceOf(Resource.Success::class.java)
            // ¿el tamaño de la lista esperada es correto?
            assertThat(valueReturned.data?.size).isEqualTo(71)
            // ¿el contenido es el esperado?
            valueReturned.data?.get(0)?.apply {
                assertThat(id).isEqualTo(2449357)
                assertThat(airdate).isEqualTo("2022-12-01")
                assertThat(airtime).isEqualTo("00:00")
                assertThat(showInfo.id).isEqualTo(59312)
                assertThat(showInfo.image?.medium)
                    .isEqualTo("https://static.tvmaze.com/uploads/images/medium_portrait/441/1104117.jpg")
                assertThat(showInfo.network?.officialSite).isEqualTo("https://www.sho.com/")
            }
        }
    }

    /**
     * test que valida el resultado de la ejecución de
     * [com.example.tvmazeclient.data.repository.ShowsRepositoryImpl.getShowsSchedule]
     * cuando la respuesta del servidor es exitosa
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
            val responseBody = service.getSchedule(country,date)
            Mockito.`when`(dataSource.getCurrentShows(country, date)).thenReturn(responseBody)

            /**
             * instanciar objeto a analizar
             */
            repository = ShowsRepositoryImpl(dataSource)

            /**
             * ejecutar metodo a analizar
             */
            val valueReturned = repository.getShowsSchedule(country, date)

            /**
             * validar resultados
             */
            // ¿dataSource.getCurrentShows se ejecuta 1 vez?
            Mockito.verify(dataSource, Mockito.times(1))
                .getCurrentShows(country, date)
            // ¿el valor retornado es nulo?
            assertThat(valueReturned).isNotNull()
            // ¿el valor retornado es una instancia de Resource.Error?
            assertThat(valueReturned).isInstanceOf(Resource.Error::class.java)
            // ¿contiene un mensaje de error?
            assertThat(valueReturned.message).isNotEmpty()
        }
    }
}