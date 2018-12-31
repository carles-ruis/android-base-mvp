package com.carles.carleskotlin.poi.data.datasource

import android.content.SharedPreferences
import com.carles.carleskotlin.common.data.datasource.BaseLocalDatasource
import com.carles.carleskotlin.common.setCacheExpirationTime
import com.carles.carleskotlin.poi.data.entity.PoiRealmObject
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.toModel
import com.carles.carleskotlin.poi.toRealmObject
import io.reactivex.Maybe
import io.realm.Realm
import io.realm.kotlin.where

class PoiLocalDatasource(sharedPreferences: SharedPreferences) : BaseLocalDatasource(sharedPreferences) {

    fun getPoiList() : Maybe<List<Poi>> = Maybe.empty()

    fun getPoiDetail(id:String) : Maybe<Poi> {
        if (isExpired(PoiRealmObject::class.java.name, id)) return Maybe.empty()

        val realm = Realm.getDefaultInstance()
        val poiRealmObject = realm.where<PoiRealmObject>().equalTo(PoiRealmObject.ID, id).findFirst()
        val poi = poiRealmObject?.toModel()
        realm.close()
        return if (poi == null) Maybe.empty() else Maybe.just(poi)
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