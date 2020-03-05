package com.carles.carleskotlin.poi.usecase

import com.carles.carleskotlin.AppSchedulers
import com.carles.carleskotlin.poi.data.PoiRepository
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Single

class GetPoiDetailUsecase(val repository: PoiRepository, val schedulers: AppSchedulers) {

    operator fun invoke(id: String): Single<Poi> = repository.getPoiDetail(id).subscribeOn(schedulers.io).observeOn(schedulers.ui)
}