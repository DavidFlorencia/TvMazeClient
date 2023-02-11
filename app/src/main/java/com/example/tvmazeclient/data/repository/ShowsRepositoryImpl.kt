package com.example.tvmazeclient.data.repository

import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.model.ShowResponse
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository
import retrofit2.Response

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource
): ShowsRepository {
    /**
     * metodo que consume shows del día
     */
    override suspend fun getShowsSchedule(country: String, date: String): Resource<ScheduleResponse> {
        return responseToResource(showsRemoteDataSource.getCurrentShows(country, date))
    }

    /**
     * metodo que consume shows por busqueda
     */
    override suspend fun getShowsByQuery(query: String): Resource<QueryResponse> {
        return responseToResource(showsRemoteDataSource.getShowsByQuery(query))
    }

    override suspend fun getShowById(id: String): Resource<ShowResponse> {
        return responseToResource(showsRemoteDataSource.getShowById(id))
    }

    override suspend fun getCastById(id: String): Resource<CastResponse> {
        return responseToResource(showsRemoteDataSource.getCastById(id))
    }

    /**
     * metodo que convierte respuesta del servicio en modelo de datos
     */
    private fun <T> responseToResource(response:Response<T>):Resource<T>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}