package com.carles.carleskotlin.poi.model

data class Poi(
    val id: String,
    val title: String? = null,
    val address: String? = null,
    val transport: String? = null,
    val description: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val geocoordinates: String? = null
)