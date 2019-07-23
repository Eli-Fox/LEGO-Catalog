

package com.elifox.legocatalog.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.elifox.legocatalog.LegoSetListFragment
import com.elifox.legocatalog.data.LegoSet
import com.elifox.legocatalog.data.LegoSetRepository

/**
 * The ViewModel for [LegoSetListFragment].
 */
class LegoSetListViewModel internal constructor(legoSetRepository: LegoSetRepository) : ViewModel() {

    private val growZoneNumber = MutableLiveData<Int>().apply { value = NO_GROW_ZONE }

    val legoSets: LiveData<List<LegoSet>> = growZoneNumber.switchMap {
        legoSetRepository.getLegoSets()
        /*

        if (it == NO_GROW_ZONE) {
            legoSetRepository.getLegoSets()
        } else {
            legoSetRepository.getPlantsWithGrowZoneNumber(it)
        }

        */
    }

    // TODO filtering
    fun setGrowZoneNumber(num: Int) {
        growZoneNumber.value = num
    }

    fun clearGrowZoneNumber() {
        growZoneNumber.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZoneNumber.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
    }
}
