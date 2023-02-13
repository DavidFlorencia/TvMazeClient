package com.example.tvmazeclient.data.repository.dataSourceImpl

import com.example.tvmazeclient.data.db.LocalCast
import com.example.tvmazeclient.data.db.LocalDao
import com.example.tvmazeclient.data.db.LocalShow
import com.example.tvmazeclient.data.repository.dataSource.ShowsLocalDataSource
import kotlinx.coroutines.flow.Flow

class ShowsLocalDataSourceImpl(private val localDao: LocalDao):
    ShowsLocalDataSource {
    override suspend fun saveShow(show: LocalShow, cast: List<LocalCast>) {
        localDao.insert(show)
        localDao.insertCast(cast)
    }

    override suspend fun deleteShow(show: LocalShow) {
        localDao.delete(show)
    }

    override fun getSavedShows(): Flow<List<LocalShow>> = localDao.getSavedShows()

    override fun getShowById(id: String) = localDao.getShowById(id)

    override fun getCastById(id: String) = localDao.getCastById(id)
}