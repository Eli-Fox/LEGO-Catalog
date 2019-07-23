

package com.elifox.legocatalog.di

import android.content.Context
import com.elifox.legocatalog.data.AppDatabase
import com.elifox.legocatalog.garden.data.GardenPlantingRepository
import com.elifox.legocatalog.garden.ui.GardenPlantingListViewModelFactory
import com.elifox.legocatalog.legoset.data.LegoRemoteDataSource
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import com.elifox.legocatalog.legoset.ui.PlantDetailViewModelFactory
import com.elifox.legocatalog.legoset.ui.PlantListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPlantRepository(context: Context): LegoSetRepository {
        return LegoSetRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).legoSetDao(),
                LegoRemoteDataSource(AppModule().legoService()))
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

    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        return PlantListViewModelFactory(repository)
    }

    fun providePlantDetailViewModelFactory(
        context: Context,
        plantId: String
    ): PlantDetailViewModelFactory {
        return PlantDetailViewModelFactory(getPlantRepository(context),
                getGardenPlantingRepository(context), plantId)
    }
}
