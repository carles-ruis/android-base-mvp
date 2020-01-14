package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.createEmptyPoiListResponseDto
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.createPoiListResponseDto
import com.carles.carleskotlin.createPoiResponseDto
import com.carles.carleskotlin.poi.model.Poi
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import java.util.*

class MapperTest {

    @Test
    fun poiListResponseDto_toModel() {
        Assert.assertEquals(Collections.emptyList<Poi>(), createEmptyPoiListResponseDto().toModel())

        val dto = createPoiListResponseDto()
        Assert.assertTrue(with(dto.toModel()) {
            size == dto.list!!.size && get(0).id == dto.list!!.get(0).id
        })
    }

    @Test
    fun poiResponseDto_toModel() {
        val dto = createPoiResponseDto()
        Assert.assertTrue(with(dto.toModel()) {
            id == dto.id && title == dto.title && transport == dto.transport && email == dto.email && phone == dto.phone
        })

        dto.transport = ""; Assert.assertNull(dto.toModel().transport)
        dto.email = "null"; Assert.assertNull(dto.toModel().email)
        dto.phone = "undefined"; Assert.assertNull(dto.toModel().phone)
    }

    @Test
    fun poi_toRealmObject() {
        val poi = createPoi(System.currentTimeMillis().toString())
        Assert.assertEquals(poi.id, poi.toVo().id)
    }

    @Test
    fun poiRealmObject_toModel() {
        val poiRealmObject = PoiVo(id = System.currentTimeMillis().toString())
        assertEquals(poiRealmObject.id, poiRealmObject.toModel().id)
    }
}