package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.model.Poi
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PoiVo(
        @PrimaryKey var id: String = "",
        var title: String? = null,
        var address: String? = null,
        var transport: String? = null,
        var description: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var geocoordinates: String? = null
) : RealmObject() {

    companion object {
        val ID = "id"
    }
}

fun Poi.toVo(): PoiVo =
        PoiVo(id, title, address, transport, description, email, phone, geocoordinates)

fun PoiVo.toModel(): Poi = Poi(id, title, address, transport, description, email, phone, geocoordinates)
