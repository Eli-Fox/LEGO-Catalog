package com.elifox.legocatalog.legoset.data

import com.elifox.legocatalog.api.BaseDataSource
import com.elifox.legocatalog.api.LegoService

/**
 * Works with the Lego API to get data.
 */
//class LegoRemoteDataSource @Inject constructor(private val service: LegoService) {
class LegoSetRemoteDataSource constructor(private val service: LegoService) : BaseDataSource() {

    suspend fun fetchSets(page: Int, pageSize: Int? = null, themeId: Int? = null)
            = getResult { service.getSets(page, pageSize, themeId, "name") }

    suspend fun fetchSet(id: String)
            = getResult { service.getSet(id) }
}
