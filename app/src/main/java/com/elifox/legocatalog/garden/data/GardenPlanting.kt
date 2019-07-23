package com.elifox.legocatalog.garden.data

import androidx.room.*
import java.util.*

/**
 * [GardenPlanting] represents when a user adds a [Plant] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * legoSet).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "garden_plantings",
    foreignKeys = [ForeignKey(entity = Plant::class, parentColumns = ["id"], childColumns = ["plant_id"])],
    indices = [Index("plant_id")]
)
data class GardenPlanting(
    @ColumnInfo(name = "plant_id") val plantId: String,

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the legoSet.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the legoSet.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var gardenPlantingId: Long = 0
}