package com.carles.carleskotlin.poi.data

import android.content.SharedPreferences
import com.carles.carleskotlin.common.data.*
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Maybe
import io.realm.Realm
import io.realm.kotlin.where

class PoiLocalDatasource(private val cache: Cache) {

    fun getPoiList(): Maybe<List<Poi>> = Maybe.empty()

    fun getPoiDetail(id: String): Maybe<Poi> = Maybe.defer {
        var poi: Poi? = null
        if (!cache.isExpired(CacheItems.POI_VO, id)) {
            val realm = Realm.getDefaultInstance()
            val poiRealmObject = realm.where<PoiVo>().equalTo(PoiVo.ID, id).findFirst()
            poi = poiRealmObject?.toModel()
            realm.close()
        }
        if (poi == null) Maybe.empty() else Maybe.just(poi)
    }

    fun persist(poi: Poi) {
        val poiRealmObject = poi.toVo()
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(poiRealmObject)
        }
        realm.close()
        cache.set(CacheItems.POI_VO, poi.id)
    }

}