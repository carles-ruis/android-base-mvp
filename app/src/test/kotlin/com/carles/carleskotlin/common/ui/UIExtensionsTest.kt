package com.carles.carleskotlin.common.ui

import com.carles.carleskotlin.R
import org.junit.Assert
import org.junit.Test

class UIExtensionsTest {
    @Test
    fun throwable_shouldGetMessageId() {
        Assert.assertEquals(R.string.error_server_response, Throwable().getMessageId())
    }
}