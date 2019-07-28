package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The ViewModel for [LegoSetsFragment].
 */
class LegoSetsViewModel @Inject constructor(private val repository: LegoSetRepository) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var themeId: Int? = null

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
