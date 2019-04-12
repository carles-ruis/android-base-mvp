package com.carles.carleskotlin.common.data

import android.content.SharedPreferences
import io.mockk.*
import org.junit.Test

class DataExtensionsTest {

    @Test
    fun sharedPreferences_shouldGetCacheExpirationTime() {
        val spy = spyk<SharedPreferences>()
        every { spy.getLong(any(), any()) } returns 0L
        spy.getCacheExpirationTime("someclass", "1")
        verify { spy.getLong("expiration_time_someclass1", 0L) }
    }

    @Test
    fun sharedPreferences_shouldSetCacheExpirationTime() {
        val spy = spyk<SharedPreferences>()
        every { spy.edit().putLong(any(), any()).apply() } just Runs
        spy.setCacheExpirationTime("someclass", "1", 99L)
        verify { spy.edit().putLong("expiration_time_someclass1", 99L) }
    }
}