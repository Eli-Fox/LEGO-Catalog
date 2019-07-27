

package com.elifox.legocatalog.di

import android.content.Context
import com.elifox.legocatalog.data.AppDatabase
import com.elifox.legocatalog.garden.data.GardenPlantingRepository
import com.elifox.legocatalog.garden.ui.GardenPlantingListViewModelFactory
import com.elifox.legocatalog.legoset.data.LegoSetRemoteDataSource
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import com.elifox.legocatalog.legoset.ui.LegoSetViewModelFactory
import com.elifox.legocatalog.legoset.ui.LegoSetsViewModelFactory
import com.elifox.legocatalog.legotheme.data.LegoThemeRemoteDataSource
import com.elifox.legocatalog.legotheme.data.LegoThemeRepository
import com.elifox.legocatalog.legotheme.ui.LegoThemeViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getLegoSetRepository(context: Context): LegoSetRepository {
        return LegoSetRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).legoSetDao(),
                LegoSetRemoteDataSource(AppModule().legoService()))
    }

    private fun getLegoThemeRepository(context: Context): LegoThemeRepository {
        return LegoThemeRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).legoThemeDao(),
                LegoThemeRemoteDataSource(AppModule().legoService()))
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).gardenPlantingDao())
    }

    fun provideGardenPlantingListViewModelFactory(
        context: Context
    ): GardenPlantingListViewModelFactory {
        val repository = getGardenPlantingRepository(context)
        return GardenPlantingListViewModelFactory(repository)
    }

    fun provideLegoSetsViewModelFactory(context: Context, connectivityAvailable: Boolean,
                                        themeId: Int?): LegoSetsViewModelFactory {
        val repository = getLegoSetRepository(context)
        return LegoSetsViewModelFactory(repository, connectivityAvailable, themeId)
    }

    fun provideLegoThemeViewModelFactory(context: Context): LegoThemeViewModelFactory {
        val repository = getLegoThemeRepository(context)
        return LegoThemeViewModelFactory(repository)
    }

    fun provideLegoSetViewModelFactory(context: Context, id: String): LegoSetViewModelFactory {
        return LegoSetViewModelFactory(getLegoSetRepository(context), id)
    }
}
