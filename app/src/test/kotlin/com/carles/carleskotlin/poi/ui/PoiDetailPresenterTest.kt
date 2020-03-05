package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.R
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.poi.data.PoiRepository
import com.carles.carleskotlin.poi.usecase.GetPoiDetailUsecase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class PoiDetailPresenterTest {

    val view: PoiDetailView = mockk(relaxed = true)
    val getPoiDetailUsecase: GetPoiDetailUsecase = mockk()
    val presenter = PoiDetailPresenter(view, "some_id", getPoiDetailUsecase)

    @Test
    fun onViewCreated_getPoiDetailSuccess() {
        every { getPoiDetailUsecase.invoke("some_id") } returns Single.just(createPoi("some_poi"))
        presenter.onViewCreated()
        verify { getPoiDetailUsecase.invoke("some_id") }
        verify { with(view) { showProgress(); hideProgress(); displayPoiDetail(any()) } }
    }

    @Test
    fun onViewCreated_getPoiDetailError() {
        every { getPoiDetailUsecase.invoke("some_id") } returns Single.error(Throwable())
        presenter.onViewCreated()
        verify { getPoiDetailUsecase.invoke("some_id") }
        verify { with(view) { showProgress(); showError(R.string.error_server_response, any()) } }
    }
}