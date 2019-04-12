package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.model.Poi
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PoiRealmObject(
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

fun Poi.toRealmObject(): PoiRealmObject =
        PoiRealmObject(id, title, address, transport, description, email, phone, geocoordinates)

fun PoiRealmObject.toModel(): Poi = Poi(id, title, address, transport, description, email, phone, geocoordinates)
