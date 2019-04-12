package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.poi.model.Poi
import io.mockk.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class PoiCloudDatasourceTest {

    private lateinit var datasource: PoiCloudDatasource
    private val localDatasource: PoiLocalDatasource = mockk()
    private val poiDetail = Poi("the_poi")
    private val poiList = listOf(poiDetail)
    private val service: PoiService = mockk()

    @Before
    fun setup() {
        datasource = PoiCloudDatasource(localDatasource, service)
    }

    @Test
    fun getPoiList_shouldPerformRequest() {
        mockkStatic("com.carles.carleskotlin.poi.data.DTOKt")
        val poiListDto = PoiListResponseDto()
        every { service.getPoiList() } returns Single.just(poiListDto)
        every { poiListDto.toModel() } returns poiList

        datasource.getPoiList().test().assertValue(poiList).assertComplete()

        verifyAll { service.getPoiList(); poiListDto.toModel() }
    }

    @Test
    fun getPoiDetail_shouldPerformRequest() {
        mockkStatic("com.carles.carleskotlin.poi.data.DTOKt")
        val poiDetailDto = PoiResponseDto("some_id")
        every { service.getPoiDetail("some_id") } returns Single.just(poiDetailDto)
        every { poiDetailDto.toModel() } returns poiDetail
        every { localDatasource.persist(any())}  just Runs

        datasource.getPoiDetail("some_id").test().assertValue(poiDetail)

        verifyAll {
            service.getPoiDetail("some_id");
            poiDetailDto.toModel()
            localDatasource.persist(poiDetail)
        }
    }
}