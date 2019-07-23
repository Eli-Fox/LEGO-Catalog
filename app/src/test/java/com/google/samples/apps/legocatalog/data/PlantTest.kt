

package com.elifox.legocatalog.data

import com.elifox.legocatalog.data.Plant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

class PlantTest {

    private lateinit var plant: Plant

    @Before fun setUp() {
        plant = Plant("1", "Tomato", "A red vegetable", 1, 2, "")
    }

    @Test fun test_default_values() {
        val defaultPlant = Plant("2", "Apple", "Description", 1)
        assertEquals(7, defaultPlant.wateringInterval)
        assertEquals("", defaultPlant.imageUrl)
    }

    @Test fun test_shouldBeWatered() {
        Calendar.getInstance().let { now ->
            // Generate lastWateringDate from being as copy of now.
            val lastWateringDate = Calendar.getInstance()

            // Test for lastWateringDate is today.
            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -0) }))

            // Test for lastWateringDate is yesterday.
            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -1) }))

            // Test for lastWateringDate is the day before yesterday.
            lastWateringDate.time = now.time
            assertFalse(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -2) }))

            // Test for lastWateringDate is some days ago, three days ago, four days ago etc.
            lastWateringDate.time = now.time
            assertTrue(plant.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -3) }))
        }
    }

    @Test fun test_toString() {
        assertEquals("Tomato", plant.toString())
    }
}