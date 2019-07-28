

package com.elifox.legocatalog.legotheme.ui

import androidx.lifecycle.ViewModel
import com.elifox.legocatalog.legotheme.data.LegoThemeRepository

/**
 * The ViewModel for [LegoThemeFragment].
 */
class LegoThemeViewModel internal constructor(repository: LegoThemeRepository) : ViewModel() {

    val legoThemes= repository.themes
}
