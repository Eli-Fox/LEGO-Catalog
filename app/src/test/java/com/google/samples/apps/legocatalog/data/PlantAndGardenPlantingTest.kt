

package com.elifox.legocatalog.data

import org.junit.Assert.assertTrue
import org.junit.Test

class PlantAndGardenPlantingTest {

    @Test fun test_default_values() {
        val p = PlantAndGardenPlantings()
        assertTrue(p.gardenPlantings.isEmpty())
    }
}