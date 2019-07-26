

package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.elifox.legocatalog.legoset.data.LegoSetRepository

/**
 * Factory for creating a [LegoSetsViewModel] with a constructor that takes a [LegoSetRepository].
 */
class LegoSetsViewModelFactory(
        private val repository: LegoSetRepository,
        private val themeId: Int
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = LegoSetsViewModel(repository, themeId) as T
}
