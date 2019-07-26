package com.elifox.legocatalog.legoset.data

import com.elifox.legocatalog.api.BaseDataSource
import com.elifox.legocatalog.api.LegoService

/**
 * Works with the Lego API to get data.
 */
//class LegoRemoteDataSource @Inject constructor(private val service: LegoService) {
class LegoSetRemoteDataSource constructor(private val service: LegoService) : BaseDataSource() {

    companion object {
        const val PAGE_SIZE = 30
    }

    suspend fun fetchData(page: Int, themeId: Int) = getResult(service.getSets(page, PAGE_SIZE, themeId))

    /*
    // TODO pagination, error handling, ui loading
     * Load Product Hunt data for a specific page.

    suspend fun loadData(page: Int) = safeApiCall(
            call = { requestData(page) },
            errorMessage = "Error loading ProductHunt data"
    )

    private suspend fun requestData(page: Int): Result<GetPostsResponse> {
        val response = service.getPostsAsync(page)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Error loading ProductHunt data " +
                "${response.code()} ${response.message()}"))
    }
    */
}
