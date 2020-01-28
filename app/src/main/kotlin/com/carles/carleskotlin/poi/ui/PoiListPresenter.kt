package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.common.ui.BasePresenter
import com.carles.carleskotlin.common.ui.addTo
import com.carles.carleskotlin.common.ui.getMessageId
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.data.PoiRepository
import io.reactivex.Scheduler

class PoiListPresenter(
    poiListView: PoiListView,
    val uiScheduler: Scheduler,
    val processScheduler: Scheduler,
    val poiRepository: PoiRepository
) : BasePresenter<PoiListView>(poiListView) {

    override fun onViewCreated() {
        super.onViewCreated()
        getPoiList()
    }

    private fun getPoiList() {
        view.showProgress()
        poiRepository.getPoiList().subscribeOn(processScheduler).observeOn(uiScheduler)
            .subscribe(::onGetPoiListSuccess, ::onGetPoiListError)
            .addTo(disposables)
    }

    private fun onGetPoiListSuccess(pois: List<Poi>) {
        view.hideProgress()
        view.displayPoiList(pois)
    }

    private fun onGetPoiListError(throwable: Throwable) {
        view.showError(throwable.getMessageId()) { getPoiList() }
    }

    fun onPoiClicked(poi: Poi) {
        view.navigateToPoiDetail(poi.id)
    }
}