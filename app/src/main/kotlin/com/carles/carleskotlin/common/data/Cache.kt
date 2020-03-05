package com.carles.carleskotlin.common.data

import java.util.*

enum class CacheItems { POI_VO }

class Cache {

    companion object {
        private const val EXPIRE_TIME = 1000 * 60
    }

    private val map: MutableMap<String, Long> = mutableMapOf()

    private fun getCacheKey(key: CacheItems, itemId: String) = key.toString() + "_" + itemId

    fun isCached(item: CacheItems, itemId: String): Boolean {
        val key = getCacheKey(item, itemId)
        if (map.containsKey(key) && map.get(key) ?: 0L < Calendar.getInstance().timeInMillis) {
            map.remove(key)
        }
        return map.containsKey(key)
    }

    fun set(key: CacheItems, id: String) {
        map.set(getCacheKey(key, id), Calendar.getInstance().timeInMillis + EXPIRE_TIME)
    }
}
