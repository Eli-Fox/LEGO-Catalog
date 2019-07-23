

package com.elifox.legocatalog.garden.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elifox.legocatalog.garden.data.GardenPlantingRepository

/**
 * Factory for creating a [GardenPlantingListViewModel] with a constructor that takes a
 * [GardenPlantingRepository].
 */
class GardenPlantingListViewModelFactory(
    private val repository: GardenPlantingRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GardenPlantingListViewModel(repository) as T
    }
}