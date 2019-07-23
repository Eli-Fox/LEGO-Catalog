

package com.elifox.legocatalog.utilities

import android.content.Context
import com.elifox.legocatalog.data.AppDatabase
import com.elifox.legocatalog.data.GardenPlantingRepository
import com.elifox.legocatalog.data.LegoSetRepository
import com.elifox.legocatalog.viewmodels.GardenPlantingListViewModelFactory
import com.elifox.legocatalog.viewmodels.PlantDetailViewModelFactory
import com.elifox.legocatalog.viewmodels.PlantListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPlantRepository(context: Context): LegoSetRepository {
        return LegoSetRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).legoSetDao())
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
