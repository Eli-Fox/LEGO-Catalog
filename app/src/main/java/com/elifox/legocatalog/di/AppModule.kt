package com.elifox.legocatalog.di

import com.elifox.legocatalog.BuildConfig
import com.elifox.legocatalog.api.AuthInterceptor
import com.elifox.legocatalog.api.LegoService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO DI with Dagger 2
// @Module(includes = [ViewModelModule::class])
class AppModule {

    // TODO remove
    fun legoService(): LegoService {
        val core = CoreDataModule()
        return provideLegoService(
                providePrivateOkHttpClient(core.provideOkHttpClient(core.provideLoggingInterceptor())),
                core.provideGsonConverterFactory(core.provideGson()))
    }

    //@Singleton
    //@Provides
    fun provideLegoService(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): LegoService {
        return createRetrofit(
                okhttpClient,
                converterFactory
        ).create(LegoService::class.java)
    }

    //@Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
                .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN)).build()
    }

    private fun createRetrofit(
            // TODO lazy?
            //okhttpClient: Lazy<OkHttpClient>,
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(LegoService.ENDPOINT)
                // TODO need?
                //.addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    /*
    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
            .databaseBuilder(app, GithubDb::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideUserDao(db: GithubDb): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }

    */
}
