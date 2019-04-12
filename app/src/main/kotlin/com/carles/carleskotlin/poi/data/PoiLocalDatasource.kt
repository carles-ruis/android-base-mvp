package com.carles.carleskotlin.poi.data

import android.content.SharedPreferences
import com.carles.carleskotlin.common.data.BaseLocalDatasource
import com.carles.carleskotlin.common.data.setCacheExpirationTime
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Maybe
import io.realm.Realm
import io.realm.kotlin.where

class PoiLocalDatasource(sharedPreferences: SharedPreferences) : BaseLocalDatasource(sharedPreferences) {

    fun getPoiList() : Maybe<List<Poi>> = Maybe.empty()

    fun getPoiDetail(id:String) : Maybe<Poi> = Maybe.defer {
        var poi : Poi? = null
        if (!isExpired(PoiRealmObject::class.java.name, id)) {
            val realm = Realm.getDefaultInstance()
            val poiRealmObject = realm.where<PoiRealmObject>().equalTo(PoiRealmObject.ID, id).findFirst()
            poi = poiRealmObject?.toModel()
            realm.close()
        }
        if (poi == null) Maybe.empty() else Maybe.just(poi)
    }

    fun persist(poi:Poi){
        val poiRealmObject = poi.toRealmObject()
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealmOrUpdate(poiRealmObject)
        }
        realm.close()
        sharedPreferences.setCacheExpirationTime(PoiRealmObject::class.java.name, poi.id, calculateCacheExpirationTime())
    }

}