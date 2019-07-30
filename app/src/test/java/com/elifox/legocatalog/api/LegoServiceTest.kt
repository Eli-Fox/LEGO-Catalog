package com.elifox.legocatalog.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class LegoServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: LegoService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LegoService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestLegoSets() {
        runBlocking {
            enqueueResponse("legosets.json")
            val resultResponse = service.getSets().body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/lego/sets/"))
        }
    }

    @Test
    fun getLegoSetsResponse() {
        runBlocking {
            enqueueResponse("legosets.json")
            val resultResponse = service.getSets().body()
            val legoSets = resultResponse!!.results

            assertThat(resultResponse.count, `is`(9))
            assertThat(legoSets.size, `is`(9))
        }
    }

    @Test
    fun getLegoSetsPagination() {
        runBlocking {
            enqueueResponse("legosets.json")
            val resultResponse = service.getSets().body()

            assertNull(resultResponse!!.next)
            assertNull(resultResponse.previous)
        }
    }


    @Test
    fun getLegoSetItem() {
        runBlocking {
            enqueueResponse("legosets.json")
            val resultResponse = service.getSets().body()
            val legoSets = resultResponse!!.results

            val legoSet = legoSets[0]
            assertThat(legoSet.id, `is`("30212-1"))
            assertThat(legoSet.name, `is`("Mirkwood Elf Guard"))
            assertThat(legoSet.year, `is`(2012))
            assertThat(legoSet.themeId, `is`(563))
            assertThat(legoSet.numParts, `is`(27))
            assertThat(legoSet.imageUrl, `is`("https://cdn.rebrickable.com/media/sets/30212-1.jpg"))
            assertThat(legoSet.url, `is`("https://rebrickable.com/sets/30212-1/mirkwood-elf-guard/"))
            assertThat(legoSet.lastModifiedDate, `is`("2016-04-23T12:25:04.325081Z"))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
                source.readString(Charsets.UTF_8))
        )
    }
}
