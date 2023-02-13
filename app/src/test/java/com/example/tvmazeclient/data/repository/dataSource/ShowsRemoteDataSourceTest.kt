package com.example.tvmazeclient.data.repository.dataSource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tvmazeclient.data.api.ShowsApiService
import com.example.tvmazeclient.data.repository.dataSourceImpl.ShowsRemoteDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

class ShowsRemoteDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var api: ShowsApiService
    private lateinit var dataSource: ShowsRemoteDataSource

    @Before
    fun setUp(){
        api = Mockito.mock(ShowsApiService::class.java)
        dataSource = ShowsRemoteDataSourceImpl(api)
    }

    @Test
    fun `when invoke getCurrentShows then getSchedule is called 1 times`(): Unit = runBlocking {
        val country = "US"
        val date = "today"

        dataSource.getCurrentShows(country,date)
        Mockito.verify(api,times(1)).getSchedule(country, date)
    }

    @Test
    fun `when invoke dataSource_getCurrentShows then api_getShowsByQuery is called 1 times`(): Unit = runBlocking {
        val query = "string"

        dataSource.getShowsByQuery(query)
        Mockito.verify(api,times(1)).getShowsByQuery(query)
    }

    @Test
    fun `when invoke dataSource_getShowById then api_getShowById is called 1 times`(): Unit = runBlocking {
        val id = 12516

        dataSource.getShowById(id.toString())
        Mockito.verify(api,times(1)).getShowById(id.toString())
    }

    @Test
    fun `when invoke dataSource_getCastById then api_getCastById is called 1 times`(): Unit = runBlocking {
        val id = 12516

        dataSource.getCastById(id.toString())
        Mockito.verify(api,times(1)).getCastById(id.toString())
    }
}