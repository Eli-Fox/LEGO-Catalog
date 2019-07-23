

package com.elifox.legocatalog.legoset.data

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 * Repository module for handling data operations.
 */
class LegoSetRepository private constructor(private val dao: LegoSetDao,
                                            private val remoteDataSource: LegoRemoteDataSource) {

    // TODO paging, error, loading
    val sets = liveData(Dispatchers.IO) {
        emitSource(dao.getLegoSets())
        val remoteSets = remoteDataSource.fetchData(1)
        dao.insertAll(remoteSets)
    }

    fun getLegoSet(plantId: String) = dao.getLegoSet(plantId)

    fun getDistinctLegoSet(id: String) = getLegoSet(id).distinctUntilChanged()

    // TODO filtered
    //fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
    //        dao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: LegoSetRepository? = null

        fun getInstance(dao: LegoSetDao, remoteDataSource: LegoRemoteDataSource) =
                instance ?: synchronized(this) {
                    instance
                            ?: LegoSetRepository(dao, remoteDataSource).also { instance = it }
                }
    }
}
