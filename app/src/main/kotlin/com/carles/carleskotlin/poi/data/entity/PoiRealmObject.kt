package com.carles.carleskotlin.poi.data.entity

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