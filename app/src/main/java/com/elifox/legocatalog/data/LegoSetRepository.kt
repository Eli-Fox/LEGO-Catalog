

package com.elifox.legocatalog.data

/**
 * Repository module for handling data operations.
 */
class LegoSetRepository private constructor(private val legoSetDao: LegoSetDao) {

    fun getLegoSet(plantId: String) = legoSetDao.getLegoSet(plantId)

    fun getLegoSets() = legoSetDao.getLegoSets()

    // TODO filtered
    //fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
    //        legoSetDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: LegoSetRepository? = null

        fun getInstance(legoSetDao: LegoSetDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: LegoSetRepository(legoSetDao).also { instance = it }
                }
    }

    /*
    // TODO coroutines, network fetch

    suspend fun createGardenPlanting(plantId: String) {
        withContext(IO) {
            val gardenPlanting = GardenPlanting(plantId)
            gardenPlantingDao.insertGardenPlanting(gardenPlanting)
        }
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        withContext(IO) {
            gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
        }
    }

    fun getGardenPlantingForPlant(plantId: String) =
            gardenPlantingDao.getGardenPlantingForPlant(plantId)

    fun getGardenPlantings() = gardenPlantingDao.getGardenPlantings()

    fun getPlantAndGardenPlantings() = gardenPlantingDao.getPlantAndGardenPlantings()

     */
}
