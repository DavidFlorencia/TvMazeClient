package com.example.tvmazeclient.data.repository

import com.example.tvmazeclient.data.db.LocalCast
import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.data.model.CastResponse
import com.example.tvmazeclient.data.model.QueryResponse
import com.example.tvmazeclient.data.model.ScheduleResponse
import com.example.tvmazeclient.data.model.ShowResponse
import com.example.tvmazeclient.data.repository.dataSource.ShowsLocalDataSource
import com.example.tvmazeclient.data.repository.dataSource.ShowsRemoteDataSource
import com.example.tvmazeclient.data.util.Resource
import com.example.tvmazeclient.domain.ShowsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ShowsRepositoryImpl(
    private val showsRemoteDataSource: ShowsRemoteDataSource,
    private val showsLocalDataSource: ShowsLocalDataSource
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

    override suspend fun saveShow(show: LocalShow, cast: List<LocalCast>) =
        showsLocalDataSource.saveShow(show, cast)

    override suspend fun deleteShow(show: LocalShow) =
        showsLocalDataSource.deleteShow(show)

    override fun getSavedShows(): Flow<List<LocalShow>> =
        showsLocalDataSource.getSavedShows()

    override fun getLocalShowById(id: String) =
        showsLocalDataSource.getShowById(id)

    override fun getLocalCastById(id: String) =
        showsLocalDataSource.getCastById(id)
}