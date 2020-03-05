package com.carles.carleskotlin.poi.usecase

import com.carles.carleskotlin.AppSchedulers
import com.carles.carleskotlin.createPoiList
import com.carles.carleskotlin.poi.data.PoiRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class GetPoiListUsecaseTest {

    val scheduler = TestScheduler()
    val schedulers = AppSchedulers(scheduler, scheduler)
    val repository: PoiRepository = mockk()
    val usecase = GetPoiListUsecase(repository, schedulers)

    @Test
    fun invoke_accessRepository() {
        val expectedList = createPoiList("1","2")
        every { repository.getPoiList() } returns Single.just(expectedList)

        val result = usecase.invoke().test()
        scheduler.triggerActions()

        result.assertValue(expectedList).assertComplete()
        verify { repository.getPoiList() }
    }

}