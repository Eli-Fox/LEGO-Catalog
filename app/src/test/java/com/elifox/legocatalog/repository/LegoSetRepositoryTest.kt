package com.elifox.legocatalog.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elifox.legocatalog.api.LegoService
import com.elifox.legocatalog.data.AppDatabase
import com.elifox.legocatalog.legoset.data.LegoSetDao
import com.elifox.legocatalog.legoset.data.LegoSetRemoteDataSource
import com.elifox.legocatalog.legoset.data.LegoSetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class LegoSetRepositoryTest {
    private lateinit var repository: LegoSetRepository
    private val dao = mock(LegoSetDao::class.java)
    private val service = mock(LegoService::class.java)
    private val remoteDataSource = LegoSetRemoteDataSource(service)
    private val mockRemoteDataSource = spy(remoteDataSource)

    private val themeId = 456
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.legoSetDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = LegoSetRepository(dao, remoteDataSource)
    }

    @Test
    fun loadLegoSetsFromNetwork() {
        runBlocking {
            repository.observePagedSets(connectivityAvailable = true,
                    themeId = themeId, coroutineScope = coroutineScope)

            verify(dao, never()).getLegoSets(themeId)
            verifyZeroInteractions(dao)
        }
    }

}