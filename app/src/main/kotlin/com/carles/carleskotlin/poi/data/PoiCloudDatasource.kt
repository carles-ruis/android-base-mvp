package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Single

class PoiCloudDatasource(private val poiLocalDatasource: PoiLocalDatasource, private val poiService: PoiService) {

    fun getPoiList(): Single<List<Poi>> = poiService.getPoiList().map { it.toModel() }

    fun getPoiDetail(id: String): Single<Poi> =
            poiService.getPoiDetail(id).map { it.toModel() }.doOnSuccess { poiLocalDatasource.persist(it) }

}