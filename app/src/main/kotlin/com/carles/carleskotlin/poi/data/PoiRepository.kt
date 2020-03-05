package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.data.PoiCloudDatasource
import com.carles.carleskotlin.poi.data.PoiLocalDatasource
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Maybe
import io.reactivex.Single

class PoiRepository(private val poiLocalDatasource: PoiLocalDatasource, private val poiCloudDatasource: PoiCloudDatasource) {

    fun getPoiList(): Single<List<Poi>> =
        Maybe.concat(poiLocalDatasource.getPoiList(), poiCloudDatasource.getPoiList().toMaybe()).firstOrError();

    fun getPoiDetail(id: String): Single<Poi> =
        Maybe.concat(
            poiLocalDatasource.getPoiDetail(id),
            poiCloudDatasource.getPoiDetail(id).doOnSuccess { poiLocalDatasource.persist(it) }.toMaybe()
        ).firstOrError();
}