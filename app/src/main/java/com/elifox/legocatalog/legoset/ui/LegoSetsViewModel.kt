package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * The ViewModel for [LegoSetsFragment].
 */
class LegoSetsViewModel internal constructor(private val repository: LegoSetRepository,
                                             private val connectivityAvailable: Boolean,
                                             private val themeId: Int? = null) : ViewModel() {


    private val ioCoroutineScope = CoroutineScope(Dispatchers.IO)

    val legoSets by lazy { repository.observePagedSets(
            connectivityAvailable, themeId, ioCoroutineScope) }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
