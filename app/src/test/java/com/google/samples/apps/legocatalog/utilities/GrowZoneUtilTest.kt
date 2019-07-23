

package com.elifox.legocatalog.util

import org.junit.Assert.assertEquals
import org.junit.Test

class GrowZoneUtilTest {

    @Test fun getZoneForLatitude() {
        assertEquals(13, com.elifox.legocatalog.util.getZoneForLatitude(0.0))
        assertEquals(13, com.elifox.legocatalog.util.getZoneForLatitude(7.0))
        assertEquals(12, com.elifox.legocatalog.util.getZoneForLatitude(7.1))
        assertEquals(1, com.elifox.legocatalog.util.getZoneForLatitude(84.1))
        assertEquals(1, com.elifox.legocatalog.util.getZoneForLatitude(90.0))
    }

    @Test fun getZoneForLatitude_negativeLatitudes() {
        assertEquals(13, com.elifox.legocatalog.util.getZoneForLatitude(-7.0))
        assertEquals(12, com.elifox.legocatalog.util.getZoneForLatitude(-7.1))
        assertEquals(1, com.elifox.legocatalog.util.getZoneForLatitude(-84.1))
        assertEquals(1, com.elifox.legocatalog.util.getZoneForLatitude(-90.0))
    }

    // Bugfix test for https://github.com/googlesamples/android-sunflower/issues/8
    @Test fun getZoneForLatitude_GitHub_issue8() {
        assertEquals(9, com.elifox.legocatalog.util.getZoneForLatitude(35.0))
        assertEquals(8, com.elifox.legocatalog.util.getZoneForLatitude(42.0))
        assertEquals(7, com.elifox.legocatalog.util.getZoneForLatitude(49.0))
        assertEquals(6, com.elifox.legocatalog.util.getZoneForLatitude(56.0))
        assertEquals(5, com.elifox.legocatalog.util.getZoneForLatitude(63.0))
        assertEquals(4, com.elifox.legocatalog.util.getZoneForLatitude(70.0))
    }
}