package com.elifox.legocatalog.legoset.data

import androidx.lifecycle.distinctUntilChanged
import com.elifox.legocatalog.data.statusLiveData

/**
 * Repository module for handling data operations.
 */
class LegoSetRepository private constructor(private val dao: LegoSetDao,
                                            private val legoSetRemoteDataSource: LegoSetRemoteDataSource) {

    // TODO paging
    fun observeSets(themeId: Int) = statusLiveData(
            databaseQuery = { dao.getLegoSets(themeId) },
            networkCall = { legoSetRemoteDataSource.fetchData(1, themeId) },
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

        fun getInstance(dao: LegoSetDao, legoSetRemoteDataSource: LegoSetRemoteDataSource) =
                instance ?: synchronized(this) {
                    instance
                            ?: LegoSetRepository(dao, legoSetRemoteDataSource).also { instance = it }
                }
    }
}
