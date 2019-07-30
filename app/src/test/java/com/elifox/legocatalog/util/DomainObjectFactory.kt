package com.elifox.legocatalog.util

import com.elifox.legocatalog.legoset.data.LegoSet

object DomainObjectFactory {

    fun createLegoSet() = LegoSet("id_123", "Fantastic Car", "imageUrl", 31)

    fun createLegoSets(count: Int): List<LegoSet> {
        return (0 until count).map {
            createLegoSet()
        }
    }

}
