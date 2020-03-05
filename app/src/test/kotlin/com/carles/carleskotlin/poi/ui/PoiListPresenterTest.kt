package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.R
import com.carles.carleskotlin.createPoi
import com.carles.carleskotlin.createPoiList
import com.carles.carleskotlin.poi.data.PoiRepository
import com.carles.carleskotlin.poi.usecase.GetPoiListUsecase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

class PoiListPresenterTest {

    val view: PoiListView = mockk(relaxed = true)
    val getPoiListUsecase : GetPoiListUsecase = mockk()
    var presenter = PoiListPresenter(view, getPoiListUsecase)

    @Test
    fun onViewCreated_getPoiListSuccess() {
        every { getPoiListUsecase.invoke() } returns Single.just(createPoiList("one", "two"))
        presenter.onViewCreated()
        verify { getPoiListUsecase.invoke() }
        verify { with(view) { showProgress(); hideProgress(); displayPoiList(any()) } }
    }

    @Test
    fun onViewCreate_getPoiListError() {
        every { getPoiListUsecase.invoke() } returns Single.error(Throwable())
        presenter.onViewCreated()
        verify { getPoiListUsecase.invoke() }
        verify { with(view) { showProgress(); showError(R.string.error_server_response, any()) } }
    }

    @Test
    fun onPoiClicked_navigateToDetail() {
        presenter.onPoiClicked(createPoi("some_poi"))
        verify { view.navigateToPoiDetail("some_poi") }
    }

}