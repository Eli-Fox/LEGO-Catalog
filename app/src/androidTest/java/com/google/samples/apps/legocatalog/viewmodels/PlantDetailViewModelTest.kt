

package com.elifox.legocatalog.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.elifox.legocatalog.data.AppDatabase
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import com.elifox.legocatalog.legoset.ui.PlantDetailViewModel
import com.elifox.legocatalog.util.getValue
import com.elifox.legocatalog.util.testPlant
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlantDetailViewModelTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: PlantDetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        val plantRepo = LegoSetRepository.getInstance(appDatabase.legoSetDao())
        val gardenPlantingRepo = GardenPlantingRepository.getInstance(
                appDatabase.gardenPlantingDao())
        viewModel = PlantDetailViewModel(plantRepo, gardenPlantingRepo, testPlant.plantId)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        assertFalse(getValue(viewModel.isPlanted))
    }
}