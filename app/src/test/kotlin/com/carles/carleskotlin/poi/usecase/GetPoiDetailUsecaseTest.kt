package com.carles.carleskotlin.poi.usecase

import com.carles.carleskotlin.AppSchedulers
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.poi.data.PoiRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class GetPoiDetailUsecaseTest() {

    val scheduler = TestScheduler()
    val schedulers = AppSchedulers(scheduler, scheduler)
    val repository : PoiRepository = mockk()
    val usecase = GetPoiDetailUsecase(repository, schedulers)

    @Test
    fun invoke_accessRepository() {
        val expectedPoi = createPoi("1")
        every { repository.getPoiDetail("1") } returns Single.just(expectedPoi)

        val result = usecase.invoke("1").test()
        scheduler.triggerActions()

        result.assertValue(expectedPoi).assertComplete()
        verify { repository.getPoiDetail("1") }
    }
}