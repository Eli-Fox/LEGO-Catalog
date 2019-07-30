package com.elifox.legocatalog.data

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class ConvertersTest {

    private val cal = Calendar.getInstance().apply {
        set(YEAR, 2077)
        set(MONTH, JULY)
        set(DAY_OF_MONTH, 7)
    }

    @Test fun calendarToDatestamp() {
        assertEquals(cal.timeInMillis, Converters().calendarToDatestamp(cal))
    }

    @Test fun datestampToCalendar() {
        assertEquals(Converters().datestampToCalendar(cal.timeInMillis), cal)
    }
}