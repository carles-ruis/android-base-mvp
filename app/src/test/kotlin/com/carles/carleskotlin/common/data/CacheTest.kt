package com.carles.carleskotlin.common.data

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class CacheTest {

   // val datasource = object : BaseLocalDatasource(sharedPreferences) {}
    val cache  = Cache()

    @Test
    fun isCached_keyNotFound() {
        assertFalse(cache.isCached(CacheItems.POI_VO, "1"))
    }

    @Test
    fun isCached_keyFound() {
        cache.set(CacheItems.POI_VO, "1")
        assertTrue(cache.isCached(CacheItems.POI_VO, "1"))
    }

    @Test
    fun isCached_keyExpired() {
        val calendar : Calendar = mockk()
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
        every { calendar.timeInMillis } returns 0L
        cache.set(CacheItems.POI_VO, "1")

        clearAllMocks()
        assertFalse(cache.isCached(CacheItems.POI_VO, "1"))
    }
}