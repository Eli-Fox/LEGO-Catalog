package com.elifox.legocatalog.di

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
// TODO remove
object InjectorUtils {

    //private fun getLegoSetRepository(context: Context): LegoSetRepository {
    //    return LegoSetRepository.getInstance(
    //            AppDatabase.getInstance(context.applicationContext).legoSetDao(),
    //            LegoSetRemoteDataSource(AppModule().legoService()))
    //}
    //
    //private fun getLegoThemeRepository(context: Context): LegoThemeRepository {
    //    return LegoThemeRepository.getInstance(
    //            AppDatabase.getInstance(context.applicationContext).legoThemeDao(),
    //            LegoThemeRemoteDataSource(AppModule().legoService()))
    //}

    //fun provideLegoSetsViewModelFactory(context: Context, connectivityAvailable: Boolean,
    //                                    themeId: Int?): LegoSetsViewModelFactory {
    //    val repository = getLegoSetRepository(context)
    //    return LegoSetsViewModelFactory(repository, connectivityAvailable, themeId)
    //}
    //
    //fun provideLegoThemeViewModelFactory(context: Context): LegoThemeViewModelFactory {
    //    val repository = getLegoThemeRepository(context)
    //    return LegoThemeViewModelFactory(repository)
    //}
    //
    //fun provideLegoSetViewModelFactory(context: Context, id: String): LegoSetViewModelFactory {
    //    return LegoSetViewModelFactory(getLegoSetRepository(context), id)
    //}
}
