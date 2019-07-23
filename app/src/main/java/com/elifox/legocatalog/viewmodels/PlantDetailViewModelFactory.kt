

package com.elifox.legocatalog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elifox.legocatalog.data.GardenPlantingRepository
import com.elifox.legocatalog.data.LegoSetRepository
import com.elifox.legocatalog.data.Plant

/**
 * Factory for creating a [PlantDetailViewModel] with a constructor that takes a [LegoSetRepository]
 * and an ID for the current [Plant].
 */
class PlantDetailViewModelFactory(
        private val legoSetRepository: LegoSetRepository,
        private val gardenPlantingRepository: GardenPlantingRepository,
        private val plantId: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantDetailViewModel(legoSetRepository, gardenPlantingRepository, plantId) as T
    }
}
