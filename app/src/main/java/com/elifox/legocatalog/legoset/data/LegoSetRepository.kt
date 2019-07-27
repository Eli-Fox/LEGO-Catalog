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

    fun observePagedSets(connectivityAvailable: Boolean, themeId: Int? = null) =
            if (connectivityAvailable) observeRemotePagedSets(themeId)
            else observeLocalPagedSets(themeId)

    private fun observeLocalPagedSets(themeId: Int? = null): LiveData<PagedList<LegoSet>> {
        val dataSourceFactory =
                if (themeId == null) dao.getPagedLegoSets()
                else dao.getPagedLegoSetsByTheme(themeId)

        return LivePagedListBuilder(dataSourceFactory,
                LegoSetPageDataSourceFactory.pagedListConfig()).build()
    }

    private fun observeRemotePagedSets(themeId: Int? = null): LiveData<PagedList<LegoSet>> {
        val dataSourceFactory = LegoSetPageDataSourceFactory(themeId, legoSetRemoteDataSource,
                dao, CoroutineScope(Dispatchers.IO))
        return LivePagedListBuilder(dataSourceFactory,
                LegoSetPageDataSourceFactory.pagedListConfig()).build()
    }

    fun observeSetsByTheme(themeId: Int) = statusLiveData(
            databaseQuery = { dao.getLegoSets(themeId) },
            networkCall = { legoSetRemoteDataSource.fetchSets(1, PAGE_SIZE, themeId) },
            saveCallResult = { dao.insertAll(it.results) })

    fun getLegoSet(plantId: String) = dao.getLegoSet(plantId)

    fun getDistinctLegoSet(id: String) = getLegoSet(id).distinctUntilChanged()

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
