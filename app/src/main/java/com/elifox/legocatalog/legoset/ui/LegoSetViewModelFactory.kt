

package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elifox.legocatalog.legoset.data.LegoSet
import com.elifox.legocatalog.legoset.data.LegoSetRepository

/**
 * Factory for creating a [LegoSetViewModel] with a constructor that takes a [LegoSetRepository]
 * and an ID for the current [LegoSet].
 */
class LegoSetViewModelFactory(
        private val legoSetRepository: LegoSetRepository,
        private val id: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LegoSetViewModel(legoSetRepository, id) as T
    }
}
