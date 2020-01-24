package com.carles.carleskotlin.common.data

import android.content.SharedPreferences

abstract class BaseLocalDatasource(private val sharedPreferences: SharedPreferences) {

    private val EXPIRE_TIME = 1000 * 60

    internal fun calculateCacheExpirationTime() = System.currentTimeMillis() + EXPIRE_TIME

    internal fun isExpired(className: String, itemId: String): Boolean =
            sharedPreferences.getCacheExpirationTime(className, itemId) < System.currentTimeMillis()
}