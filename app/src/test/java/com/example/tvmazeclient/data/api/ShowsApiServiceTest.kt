package com.example.tvmazeclient.data.api

import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * para esta batería de pruebas se utiliza
 * junit 5 para test unitarios,
 * mockwebserver para simular el consumo de datos y
 * google assert para hacer validaciones
 */
class ShowsApiServiceTest {
    private lateinit var service: ShowsApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp(){
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShowsApiService::class.java)
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
     * test que valida que la solicitud lanzada por
     * [com.example.tvmazeclient.data.api.ShowsApiService.getSchedule]
     * sea correcta
     */
    @Test
    fun getSchedule_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("scheduleResponse.json")
            val responseBody = service.getSchedule("US","today")
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/schedule?country=US&date=today")
        }
    }

    /**
     * test que valida que la solicitud lanzada por
     * [com.example.tvmazeclient.data.api.ShowsApiService.getSchedule]
     * devuelva el tamaño esperado
     */
    @Test
    fun getSchedule_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("scheduleResponse.json")
            val responseBody = service.getSchedule("US","today")
            val objectResponse: ScheduleResponse? = responseBody.body()
            assertThat(objectResponse?.size).isEqualTo(71)
        }
    }

    /**
     * test que valida que la solicitud lanzada por
     * [com.example.tvmazeclient.data.api.ShowsApiService.getSchedule]
     * devuelva el contenido esperado
     */
    @Test
    fun getSchedule_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("scheduleResponse.json")
            val responseBody = service.getSchedule("US","today")
            val objectResponse: ScheduleResponse? = responseBody.body()
            val show = objectResponse?.get(0)

            assertThat(show?.id).isEqualTo(2449357)
            assertThat(show?.airdate).isEqualTo("2022-12-01")
            assertThat(show?.airtime).isEqualTo("00:00")
            assertThat(show?.showInfo?.id).isEqualTo(59312)
            assertThat(show?.showInfo?.image?.medium)
                .isEqualTo("https://static.tvmaze.com/uploads/images/medium_portrait/441/1104117.jpg")
            assertThat(show?.showInfo?.network?.officialSite).isEqualTo("https://www.sho.com/")
        }
    }

    /**
     * test que valida que la solicitud lanzada por
     * [com.example.tvmazeclient.data.api.ShowsApiService.getShowsByQuery]
     * sea correcta
     */
    @Test
    fun getShowsByQuery_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("queryResponse.json")
            val query = "hello"
            val responseBody = service.getShowsByQuery(query)
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(request.path)
                .isEqualTo("/search/shows?q=${query}")
        }
    }

    /**
     * test que valida que la solicitud lanzada por
     * [com.example.tvmazeclient.data.api.ShowsApiService.getShowsByQuery]
     * devuelva el tamaño esperado
     */
    @Test
    fun getShowsByQuery_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("queryResponse.json")
            val query = "hello"
            val responseBody = service.getShowsByQuery(query)
            val objectResponse: QueryResponse? = responseBody.body()

            assertThat(objectResponse?.size).isEqualTo(10)
        }
    }

    /**
     * test que valida que la solicitud lanzada por
     * [com.example.tvmazeclient.data.api.ShowsApiService.getShowsByQuery]
     * devuelva el contenido esperado
     */
    @Test
    fun getShowsByQuery_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("queryResponse.json")
            val query = "hello"
            val responseBody = service.getShowsByQuery(query)
            val objectResponse: QueryResponse? = responseBody.body()
            val item = objectResponse?.get(0)

            assertThat(item?.show?.id).isEqualTo(51609)
            assertThat(item?.show?.name).isEqualTo("Hello!")
            assertThat(item?.show?.image?.medium)
                .isEqualTo("https://static.tvmaze.com/uploads/images/medium_portrait/280/701095.jpg")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}