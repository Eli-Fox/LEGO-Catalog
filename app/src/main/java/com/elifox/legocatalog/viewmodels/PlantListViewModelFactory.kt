

package com.elifox.legocatalog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.elifox.legocatalog.data.LegoSetRepository

/**
 * Factory for creating a [LegoSetListViewModel] with a constructor that takes a [LegoSetRepository].
 */
class PlantListViewModelFactory(
    private val repository: LegoSetRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = LegoSetListViewModel(repository) as T
}
