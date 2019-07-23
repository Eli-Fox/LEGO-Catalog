

package com.elifox.legocatalog.legoset.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.elifox.legocatalog.legoset.data.LegoSetRepository

/**
 * Factory for creating a [LegoSetsViewModel] with a constructor that takes a [LegoSetRepository].
 */
class PlantListViewModelFactory(
    private val repository: LegoSetRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = LegoSetsViewModel(repository) as T
}
