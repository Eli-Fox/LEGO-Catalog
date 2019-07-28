

package com.elifox.legocatalog.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elifox.legocatalog.legoset.data.LegoSet
import com.elifox.legocatalog.legoset.data.LegoSetDao
import com.elifox.legocatalog.legotheme.data.LegoTheme
import com.elifox.legocatalog.legotheme.data.LegoThemeDao

/**
 * The Room database for this app
 */
@Database(entities = [LegoTheme::class,
                      LegoSet::class],
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun legoSetDao(): LegoSetDao

    abstract fun legoThemeDao(): LegoThemeDao

}
