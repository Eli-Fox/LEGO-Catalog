

package com.elifox.legocatalog.data

import com.elifox.legocatalog.garden.data.PlantAndGardenPlantings
import org.junit.Assert.assertTrue
import org.junit.Test

class PlantAndGardenPlantingTest {

    @Test fun test_default_values() {
        val p = PlantAndGardenPlantings()
        assertTrue(p.gardenPlantings.isEmpty())
    }
}