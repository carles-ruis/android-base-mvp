package com.carles.carleskotlin.poi.usecase

import com.carles.carleskotlin.AppSchedulers
import com.carles.carleskotlin.poi.data.PoiRepository
import com.carles.carleskotlin.poi.model.Poi
import io.reactivex.Single

class GetPoiListUsecase(private val repository: PoiRepository, private val schedulers: AppSchedulers) {

    operator fun invoke() : Single<List<Poi>> = repository.getPoiList().subscribeOn(schedulers.io).observeOn(schedulers.ui)
}