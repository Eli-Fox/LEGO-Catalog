

package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legoset.data.LegoSetRepository

/**
 * The ViewModel used in [LegoSetFragment].
 */
class LegoSetViewModel(
        repository: LegoSetRepository,
        id: String) : ViewModel() {

    val legoSet = repository.observeSet(id)

}
