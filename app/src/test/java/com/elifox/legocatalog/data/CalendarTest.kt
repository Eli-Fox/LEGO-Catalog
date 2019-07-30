package com.elifox.legocatalog.data

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class CalendarTest {

    @Test
    fun testDefaultValues() {
        val cal = getInstance()
        assertYMD(cal, getInstance())
    }

    // Only Year/Month/Day precision is needed for comparing GardenPlanting Calendar entries
    private fun assertYMD(expectedCal: Calendar, actualCal: Calendar) {
        assertThat(actualCal.get(YEAR), equalTo(expectedCal.get(YEAR)))
        assertThat(actualCal.get(MONTH), equalTo(expectedCal.get(MONTH)))
        assertThat(actualCal.get(DAY_OF_MONTH), equalTo(expectedCal.get(DAY_OF_MONTH)))
    }
}