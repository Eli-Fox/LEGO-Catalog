package com.elifox.legocatalog.legoset.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.elifox.legocatalog.data.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Data source for lego sets pagination via paging library
 */
class LegoSetPageDataSource(private val themeId: Int? = null,
                            private val dataSource: LegoSetRemoteDataSource,
                            private val dao: LegoSetDao,
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
            val response = dataSource.fetchSets(page, pageSize, themeId)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!.results
                dao.insertAll(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?:  e.toString())
    }

    private fun postError(message: String) {
        // TODO timber
        Log.e(LegoSetPageDataSource::class.java.simpleName, "An error happened: $message")
        //networkState.postValue(NetworkState.FAILED)
    }

}