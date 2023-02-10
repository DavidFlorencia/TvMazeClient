package com.example.tvmazeclient.data.repository

import com.example.tvmazeclient.data.model.ApiResponse
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository
import retrofit2.Response

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource
): ShowsRepository {
    override suspend fun getShowsSchedule(country: String, date: String): Resource<ApiResponse> {
        return responseToResource(showsRemoteDataSource.getCurrentShows(country, date))
    }

    /**
     * funcion que convierte respuesta del servicio en modelo de datos
     */
    private fun responseToResource(response:Response<ApiResponse>):Resource<ApiResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}