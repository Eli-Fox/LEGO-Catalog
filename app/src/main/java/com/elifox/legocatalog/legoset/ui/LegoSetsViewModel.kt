package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legoset.data.LegoSetRepository

/**
 * The ViewModel for [LegoSetsFragment].
 */
class LegoSetsViewModel internal constructor(private val repository: LegoSetRepository,
                                             private val connectivityAvailable: Boolean,
                                             private val themeId: Int? = null) : ViewModel() {

    val legoSets by lazy { repository.observePagedSets(connectivityAvailable, themeId) }
}
