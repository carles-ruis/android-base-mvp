package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.model.Poi
import com.google.gson.annotations.SerializedName

data class PoiListResponseDto(@SerializedName("list") var list: List<PoiResponseDto>? = null)

data class PoiResponseDto(
        @SerializedName("id") var id: String,
        var title: String? = null,
        var address: String? = null,
        var transport: String? = null,
        var description: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var geocoordinates: String? = null
)

fun PoiListResponseDto.toModel(): List<Poi> =  list?.map { poiResponseDto -> poiResponseDto.toModel() } ?: emptyList()

fun PoiResponseDto.toModel(): Poi =
        Poi(id, title, address, sanitize(transport), description, sanitize(email), sanitize(phone), geocoordinates)

private fun sanitize(source: String?): String? =
        if (source == null || source.isEmpty() || source == "null" || source == "undefined") null else source