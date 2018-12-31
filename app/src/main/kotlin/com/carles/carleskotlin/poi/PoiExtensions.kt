package com.carles.carleskotlin.poi

import com.carles.carleskotlin.poi.data.entity.PoiListResponseDto
import com.carles.carleskotlin.poi.data.entity.PoiRealmObject
import com.carles.carleskotlin.poi.data.entity.PoiResponseDto
import com.carles.carleskotlin.poi.model.Poi

fun PoiListResponseDto.toModel(): List<Poi> = if (list == null) listOf() else mutableListOf<Poi>().apply {
    for (dtoItem in list!!) add(dtoItem.toModel())
}

fun PoiResponseDto.toModel(): Poi =
    Poi(id, title, address, sanitize(transport), description, sanitize(email), sanitize(phone), geocoordinates)

fun Poi.toRealmObject(): PoiRealmObject =
    PoiRealmObject(id, title, address, transport, description, email, phone, geocoordinates)

fun PoiRealmObject.toModel(): Poi = Poi(id, title, address, transport, description, email, phone, geocoordinates)

private fun sanitize(source: String?): String? =
    if (source == null || source.isEmpty() || source == "null" || source == "undefined") null else source