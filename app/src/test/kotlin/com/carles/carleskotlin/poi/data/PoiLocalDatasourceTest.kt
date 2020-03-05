package com.carles.carleskotlin.poi.data

import com.carles.carleskotlin.common.data.Cache
import com.carles.carleskotlin.common.data.CacheItems
import com.carles.carleskotlin.createPoi
import io.mockk.*
import io.realm.Realm
import org.junit.Before
import org.junit.Test

class PoiLocalDatasourceTest {

    val cache: Cache = mockk()
    val realm: Realm = mockk()
    val datasource = PoiLocalDatasource(cache)

    @Before
    fun setup() {
        mockkStatic("io.realm.Realm")
        every { Realm.getDefaultInstance() } returns realm
    }

    @Test
    fun getPoiList_shouldReturnEmpty() {
        datasource.getPoiList().test().assertNoValues().assertComplete()
    }

    @Test
    fun getPoiDetail_shouldReturnNoValuesIfCacheExpired() {
        every { cache.isCached(any(), any()) } returns false
        datasource.getPoiDetail("some_id").test().assertNoValues().assertComplete()
        verify { Realm.getDefaultInstance() wasNot Called }
    }

    @Test
    fun getPoiDetail_shouldReturnNoValuesIfNoData() {
        every { cache.isCached(any(), any()) } returns true
        every { realm.where(PoiVo::class.java).equalTo(any(), any<String>()).findFirst() } returns null
        every { realm.close() } just Runs

        datasource.getPoiDetail("some_id").test().assertNoValues().assertComplete()
        verify { realm.where(PoiVo::class.java); realm.close() }
    }

    @Test
    fun getPoiDetail_shouldReturnStoredValues() {
        every { cache.isCached(any(), any()) } returns true
        every { realm.where(PoiVo::class.java).equalTo(any(), any<String>()).findFirst() } returns PoiVo("some_id")
        every { realm.close() } just Runs

        datasource.getPoiDetail("some_id").test().assertValue(createPoi("some_id")).assertComplete()
        verify { realm.where(PoiVo::class.java); realm.close() }
    }

    @Test
    fun persist_shouldPersistToRealm() {
        val poi = createPoi("some_id")
        every { realm.executeTransaction(any()) } just Runs
        every { realm.close() } just Runs
        every { cache.set(any(), any()) } just Runs

        datasource.persist(poi)

        verify {
            realm.executeTransaction(any())
            realm.close()
            cache.set(CacheItems.POI_VO, "some_id")
        }
    }
}