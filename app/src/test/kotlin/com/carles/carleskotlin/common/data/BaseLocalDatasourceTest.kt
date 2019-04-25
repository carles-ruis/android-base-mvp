package com.carles.carleskotlin.common.data

import android.content.SharedPreferences
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Test

class BaseLocalDatasourceTest {

    val sharedPreferences: SharedPreferences = mockk(relaxed = true)
    val datasource = object : BaseLocalDatasource(sharedPreferences) {}

    @Test
    fun calculateCacheExpirationTime_shouldReturnFutureTime() {
        assertTrue(datasource.calculateCacheExpirationTime() > System.currentTimeMillis())
    }

    @Test
    fun isExpired_shouldCheckSharedPreferences() {
        datasource.isExpired("someclass", "someid")
        verify { sharedPreferences.getCacheExpirationTime("someclass", "someid") }
    }
}