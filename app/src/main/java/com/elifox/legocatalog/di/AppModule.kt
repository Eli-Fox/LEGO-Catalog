package com.elifox.legocatalog.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.android.example.github.di.ViewModelModule
import com.elifox.legocatalog.BuildConfig
import com.elifox.legocatalog.api.AuthInterceptor
import com.elifox.legocatalog.api.LegoService
import com.elifox.legocatalog.data.AppDatabase
import com.elifox.legocatalog.worker.SeedDatabaseWorker
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideLegoService(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): LegoService {
        return createRetrofit(
                okhttpClient,
                converterFactory
        ).create(LegoService::class.java)
    }

    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
                .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN)).build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) =
            Room.databaseBuilder(app, AppDatabase::class.java, "legocatalog-db")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(app).enqueue(request)
                        }
                    })
                    .build()

    @Singleton
    @Provides
    fun provideLegoSetDao(db: AppDatabase) = db.legoSetDao()


    @Singleton
    @Provides
    fun provideLegoThemeDao(db: AppDatabase) = db.legoThemeDao()

    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(LegoService.ENDPOINT)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }
}
