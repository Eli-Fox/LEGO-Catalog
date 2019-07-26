package com.elifox.legocatalog.legoset.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope

class LegoSetPageDataSourceFactory(private val dataSource: LegoSetRemoteDataSource,
                                   private val scope: CoroutineScope) : DataSource.Factory<Int, LegoSet>() {

    private val liveData = MutableLiveData<LegoSetPageDataSource>()

    override fun create(): DataSource<Int, LegoSet> {
        val source = LegoSetPageDataSource(dataSource, scope)
        liveData.postValue(source)
        return source
    }

    fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

    companion object {
        const val PAGE_SIZE = 100
    }

}