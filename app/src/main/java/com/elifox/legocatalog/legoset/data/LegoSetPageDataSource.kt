package com.elifox.legocatalog.legoset.data

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Data source for lego sets pagination via paging library
 */
class LegoSetPageDataSource(private val dataSource: LegoSetRemoteDataSource,
                            private val scope: CoroutineScope) : PageKeyedDataSource<Int, LegoSet>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, LegoSet>) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, LegoSet>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, LegoSet>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<LegoSet>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchSets(page, pageSize)
            callback(response.data!!.results)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        // TODO timber
        //Log.e(UserDataSource::class.java.simpleName, "An error happened: $e")
        //networkState.postValue(NetworkState.FAILED)
    }

}