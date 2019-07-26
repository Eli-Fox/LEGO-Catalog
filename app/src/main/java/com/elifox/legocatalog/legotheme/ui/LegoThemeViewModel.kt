

package com.elifox.legocatalog.legotheme.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legotheme.data.LegoThemeRepository

/**
 * The ViewModel for [LegoThemeFragment].
 */
class LegoThemeViewModel internal constructor(repository: LegoThemeRepository) : ViewModel() {

    private val growZoneNumber = MutableLiveData<Int>().apply { value = NO_GROW_ZONE }

    val legoThemes= repository.themes

    /*
            growZoneNumber.switchMap {
        repository.getLegoSets()


        if (it == NO_GROW_ZONE) {
            repository.getLegoSets()
        } else {
            repository.getPlantsWithGrowZoneNumber(it)
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
