

package com.elifox.legocatalog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.elifox.legocatalog.garden.data.GardenPlanting
import com.elifox.legocatalog.garden.data.GardenPlantingDao
import com.elifox.legocatalog.garden.data.Plant
import com.elifox.legocatalog.legoset.data.LegoSet
import com.elifox.legocatalog.legoset.data.LegoSetDao
import com.elifox.legocatalog.legotheme.data.LegoTheme
import com.elifox.legocatalog.legotheme.data.LegoThemeDao
import com.elifox.legocatalog.worker.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(entities = [LegoTheme::class,
                      LegoSet::class,
                      GardenPlanting::class,
                      Plant::class],
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gardenPlantingDao(): GardenPlantingDao
    abstract fun legoSetDao(): LegoSetDao
    abstract fun legoThemeDao(): LegoThemeDao

    companion object {

        const val DATABASE_NAME = "legocatalog-db"

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                        ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}
