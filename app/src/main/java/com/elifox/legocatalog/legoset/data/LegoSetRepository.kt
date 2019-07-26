package com.elifox.legocatalog.legoset.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.elifox.legocatalog.data.PagingResult
import com.elifox.legocatalog.data.Result
import com.elifox.legocatalog.data.statusLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Repository module for handling data operations.
 */
class LegoSetRepository private constructor(private val dao: LegoSetDao,
                                            private val legoSetRemoteDataSource: LegoSetRemoteDataSource) {

    fun observeLocalPagedSets(themeId: Int? = null): LiveData<PagedList<LegoSet>> {
        val dataSourceFactory =
                if (themeId == null) dao.getPagedLegoSets()
                else dao.getPagedLegoSetsByTheme(themeId)

        return LivePagedListBuilder(dataSourceFactory,
                LegoSetPageDataSourceFactory.pagedListConfig()).build()
    }

    fun fetchPagedSets(page: Int, themeId: Int? = null) =
            liveData(Dispatchers.IO) {
                val responseStatus = legoSetRemoteDataSource.fetchSets(page, PAGE_SIZE, themeId)
                if (responseStatus.status == Result.Status.SUCCESS) {
                    val data = responseStatus.data!!
                    dao.insertAll(data.results)
                    if (data.next == null) {
                        emit(PagingResult.endReached())
                    } else {
                        emit(PagingResult.success())
                    }
                } else if (responseStatus.status == Result.Status.ERROR) {
                    emit(PagingResult.error(responseStatus.message!!))
                }
            }

    fun observeRemotePagedSets(themeId: Int? = null): LiveData<PagedList<LegoSet>> {
        val dataSourceFactory = LegoSetPageDataSourceFactory(themeId, legoSetRemoteDataSource,
                CoroutineScope(Dispatchers.IO))
        return LivePagedListBuilder(dataSourceFactory,
                LegoSetPageDataSourceFactory.pagedListConfig()).build()
    }

    fun observeSetsByTheme(themeId: Int) = statusLiveData(
            databaseQuery = { dao.getLegoSets(themeId) },
            networkCall = { legoSetRemoteDataSource.fetchSets(1, PAGE_SIZE, themeId) },
            saveCallResult = { dao.insertAll(it.results) })

    fun getLegoSet(plantId: String) = dao.getLegoSet(plantId)

    fun getDistinctLegoSet(id: String) = getLegoSet(id).distinctUntilChanged()

    // TODO filtered
    //        dao.getPlantsWithGrowZoneNumber(growZoneNumber)
    //fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =

    companion object {

        const val PAGE_SIZE = 100

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
