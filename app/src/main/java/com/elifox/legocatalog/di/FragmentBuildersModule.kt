package com.elifox.legocatalog.di


import com.elifox.legocatalog.legoset.ui.LegoSetFragment
import com.elifox.legocatalog.legoset.ui.LegoSetsFragment
import com.elifox.legocatalog.legotheme.ui.LegoThemeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeThemeFragment(): LegoThemeFragment

    @ContributesAndroidInjector
    abstract fun contributeLegoSetsFragment(): LegoSetsFragment

    @ContributesAndroidInjector
    abstract fun contributeLegoSetFragment(): LegoSetFragment
}
