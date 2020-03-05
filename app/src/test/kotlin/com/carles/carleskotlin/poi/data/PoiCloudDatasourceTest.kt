package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.model.Poi
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import io.reactivex.Single
import org.junit.Test

class PoiCloudDatasourceTest {

    val poiDetail = Poi("the_poi")
    val poiList = listOf(poiDetail)
    val service: PoiService = mockk()
    val datasource = PoiCloudDatasource(service)

    @Test
    fun getPoiList_shouldPerformRequest() {
        mockkStatic("com.carles.carleskotlin.poi.data.DTOKt")
        val poiListDto = PoiListResponseDto()
        every { service.getPoiList() } returns Single.just(poiListDto)
        every { poiListDto.toModel() } returns poiList

        datasource.getPoiList().test().assertValue(poiList).assertComplete()

        verifyAll {
            service.getPoiList()
            poiListDto.toModel()
        }
    }

    @Test
    fun getPoiDetail_shouldPerformRequest() {
        mockkStatic("com.carles.carleskotlin.poi.data.DTOKt")
        val poiDetailDto = PoiResponseDto("some_id")
        every { service.getPoiDetail("some_id") } returns Single.just(poiDetailDto)
        every { poiDetailDto.toModel() } returns poiDetail

        datasource.getPoiDetail("some_id").test().assertValue(poiDetail)

        verifyAll {
            service.getPoiDetail("some_id");
            poiDetailDto.toModel()
        }
    }
}