package com.elifox.legocatalog.legoset.data

import androidx.lifecycle.distinctUntilChanged
import com.elifox.legocatalog.data.statusLiveData

/**
 * Repository module for handling data operations.
 */
class LegoSetRepository private constructor(private val dao: LegoSetDao,
                                            private val remoteDataSource: LegoRemoteDataSource) {

    // TODO paging
    val sets = statusLiveData(
            databaseQuery = { dao.getLegoSets() },
            networkCall = { remoteDataSource.fetchData(1) },
            saveCallResult = { dao.insertAll(it.results) })

    fun getLegoSet(plantId: String) = dao.getLegoSet(plantId)

    fun getDistinctLegoSet(id: String) = getLegoSet(id).distinctUntilChanged()

    // TODO filtered
    //fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
    //        dao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: LegoSetRepository? = null

        fun getInstance(dao: LegoSetDao, remoteDataSource: LegoRemoteDataSource) =
                instance ?: synchronized(this) {
                    instance
                            ?: LegoSetRepository(dao, remoteDataSource).also { instance = it }
                }
    }
}
