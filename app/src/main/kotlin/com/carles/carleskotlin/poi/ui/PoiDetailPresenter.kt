package com.carles.carleskotlin.poi.ui

import com.carles.carleskotlin.common.ui.BasePresenter
import com.carles.carleskotlin.common.ui.addTo
import com.carles.carleskotlin.common.ui.getMessageId
import com.carles.carleskotlin.poi.model.Poi
import com.carles.carleskotlin.poi.usecase.GetPoiDetailUsecase

class PoiDetailPresenter(
        poiDetailView: PoiDetailView,
        private val id: String,
        private val getPoiDetailUsecase: GetPoiDetailUsecase)
    : BasePresenter<PoiDetailView>(poiDetailView) {

    override fun onViewCreated() {
        super.onViewCreated()
        getPoiDetail()
    }

    private fun getPoiDetail() {
        view.showProgress()
        getPoiDetailUsecase(id).subscribe(::onGetPoiDetailSuccess, ::onGetPoiDetailError).addTo(disposables)
    }

    private fun onGetPoiDetailSuccess(poi: Poi) {
        view.hideProgress()
        view.displayPoiDetail(poi)
    }

    private fun onGetPoiDetailError(throwable: Throwable) {
        view.showError(throwable.getMessageId()) { getPoiDetail() }
    }
}