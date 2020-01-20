package com.carles.carleskotlin.common.data

import android.content.SharedPreferences
import org.koin.ext.getFullName
import kotlin.reflect.KClass

private val PREFERENCE_EXPIRATION_TIME_PREFIX = "expiration_time_"

fun SharedPreferences.getCacheExpirationTime(className:String, itemId:String) : Long =
        getLong(PREFERENCE_EXPIRATION_TIME_PREFIX + className + itemId, 0L)

fun SharedPreferences.setCacheExpirationTime(className: String, itemId: String, timestamp:Long) {
    edit().putLong(PREFERENCE_EXPIRATION_TIME_PREFIX + className + itemId, timestamp).apply()
}

val <T : Any> T.cacheId : String
    get() = this::class.getFullName()
