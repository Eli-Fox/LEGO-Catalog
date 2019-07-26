

package com.elifox.legocatalog.legotheme.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elifox.legocatalog.legotheme.data.LegoThemeRepository

/**
 * Factory for creating a [LegoThemeViewModel] with a constructor that takes a [LegoThemeRepository].
 */
class LegoThemeViewModelFactory(
    private val repository: LegoThemeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = LegoThemeViewModel(repository) as T
}
