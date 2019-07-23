

package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.elifox.legocatalog.garden.data.GardenPlantingRepository
import com.elifox.legocatalog.garden.data.Plant
import com.elifox.legocatalog.legoset.data.LegoSet
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [PlantDetailFragment].
 */
class PlantDetailViewModel(
        legoSetRepository: LegoSetRepository,
        private val gardenPlantingRepository: GardenPlantingRepository,
        private val plantId: String
) : ViewModel() {

    val isPlanted: LiveData<Boolean>
    val legoSet: LiveData<LegoSet>

    // TODO remove
    val plant: LiveData<Plant>? = null

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {

        /* The getGardenPlantingForPlant method returns a LiveData from querying the database. The
         * method can return null in two cases: when the database query is running and if no records
         * are found. In these cases isPlanted is false. If a record is found then isPlanted is
         * true. */
        val gardenPlantingForPlant = gardenPlantingRepository.getGardenPlantingForPlant(plantId)
        isPlanted = gardenPlantingForPlant.map { it != null }

        legoSet = legoSetRepository.getLegoSet(plantId)
    }

    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }
}
