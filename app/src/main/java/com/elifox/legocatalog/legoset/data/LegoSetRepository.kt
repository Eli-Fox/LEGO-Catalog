package com.elifox.legocatalog.legoset.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.elifox.legocatalog.data.statusLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Repository module for handling data operations.
 */
class LegoSetRepository private constructor(private val dao: LegoSetDao,
                                            private val legoSetRemoteDataSource: LegoSetRemoteDataSource) {

    fun observeSetsByTheme(themeId: Int) = statusLiveData(
            databaseQuery = { dao.getLegoSets(themeId) },
            networkCall = { legoSetRemoteDataSource.fetchSets(1, themeId) },
            saveCallResult = { dao.insertAll(it.results) })

    fun observePagedSets(): LiveData<PagedList<LegoSet>> {
        val pagedDataSource = LegoSetPageDataSourceFactory(legoSetRemoteDataSource,
                CoroutineScope(Dispatchers.IO))
        return LivePagedListBuilder(pagedDataSource, pagedDataSource.pagedListConfig()).build()
    }

    fun getLegoSet(plantId: String) = dao.getLegoSet(plantId)

    fun getDistinctLegoSet(id: String) = getLegoSet(id).distinctUntilChanged()

    // TODO filtered
    //        dao.getPlantsWithGrowZoneNumber(growZoneNumber)
    //fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =

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
