package com.carles.carleskotlin

import com.carles.carleskotlin.poi.data.PoiListResponseDto
import com.carles.carleskotlin.poi.data.PoiResponseDto
import com.carles.carleskotlin.poi.model.Poi

fun createPoi(id: String) = Poi(id = id)

fun createPoiList(vararg ids: String) = mutableListOf<Poi>().apply {
    for (id in ids) add(createPoi(id))
}

fun createEmptyPoiListResponseDto() = PoiListResponseDto(listOf())

fun createPoiListResponseDto() = PoiListResponseDto(listOf(createPoiResponseDto(), createPoiResponseDto()))

fun createPoiResponseDto() = PoiResponseDto(id = System.currentTimeMillis().toString(),
        title = "the_title",
        transport = "the_transport",
        email = "the_email",
        phone = "the_phone")
