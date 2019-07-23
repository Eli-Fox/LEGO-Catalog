

package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legoset.data.LegoSetRepository

/**
 * The ViewModel for [LegoSetsFragment].
 */
class LegoSetsViewModel internal constructor(legoSetRepository: LegoSetRepository) : ViewModel() {

    private val growZoneNumber = MutableLiveData<Int>().apply { value = NO_GROW_ZONE }

    val legoSets= legoSetRepository.sets

    /*
            growZoneNumber.switchMap {
        legoSetRepository.getLegoSets()


        if (it == NO_GROW_ZONE) {
            legoSetRepository.getLegoSets()
        } else {
            legoSetRepository.getPlantsWithGrowZoneNumber(it)
        }


    }
    */

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
