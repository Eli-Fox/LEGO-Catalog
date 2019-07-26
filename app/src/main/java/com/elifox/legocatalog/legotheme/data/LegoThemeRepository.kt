package com.elifox.legocatalog.legotheme.data

import com.elifox.legocatalog.data.statusLiveData

/**
 * Repository module for handling data operations.
 */
class LegoThemeRepository private constructor(private val dao: LegoThemeDao,
                                              private val remoteSource: LegoThemeRemoteDataSource) {

    val themes = statusLiveData(
            databaseQuery = { dao.getLegoThemes() },
            networkCall = { remoteSource.fetchData() },
            // TODO play with theme content
            saveCallResult = { dao.insertAll(it.results.filter { theme -> theme.parentId == null }) })

    // TODO filtered
    //fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
    //        dao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: LegoThemeRepository? = null

        fun getInstance(dao: LegoThemeDao, legoSetRemoteDataSource: LegoThemeRemoteDataSource) =
                instance ?: synchronized(this) {
                    instance
                            ?: LegoThemeRepository(dao, legoSetRemoteDataSource).also { instance = it }
                }
    }
}
