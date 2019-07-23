

package com.elifox.legocatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.elifox.legocatalog.data.GardenPlantingRepository
import com.elifox.legocatalog.data.PlantAndGardenPlantings

class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {

    val gardenPlantings = gardenPlantingRepository.getGardenPlantings()

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
            gardenPlantingRepository.getPlantAndGardenPlantings().map { plantings ->
                plantings.filter { it.gardenPlantings.isNotEmpty() }
            }
}