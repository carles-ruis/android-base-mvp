package com.carles.carleskotlin

import com.carles.carleskotlin.common.data.Cache
import com.carles.carleskotlin.poi.data.PoiCloudDatasource
import com.carles.carleskotlin.poi.data.PoiLocalDatasource
import com.carles.carleskotlin.poi.data.PoiRepository
import com.carles.carleskotlin.poi.data.PoiService
import com.carles.carleskotlin.poi.ui.PoiDetailPresenter
import com.carles.carleskotlin.poi.ui.PoiDetailView
import com.carles.carleskotlin.poi.ui.PoiListPresenter
import com.carles.carleskotlin.poi.ui.PoiListView
import com.carles.carleskotlin.poi.usecase.GetPoiDetailUsecase
import com.carles.carleskotlin.poi.usecase.GetPoiListUsecase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://t21services.herokuapp.com"
private val uiScheduler = named("uiScheduler")
private val processScheduler = named("processScheduler")

val appModule = module {
    single { Cache() }
    single(uiScheduler) { AndroidSchedulers.mainThread() }
    single(processScheduler) { Schedulers.io() }
    single { AppSchedulers(get(uiScheduler), get(processScheduler)) }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

val poiModule = module {
    single { (get() as Retrofit).create(PoiService::class.java) }
    single { PoiLocalDatasource(get()) }
    single { PoiCloudDatasource(get()) }
    single { PoiRepository(get(), get()) }
    factory { GetPoiDetailUsecase(get(), get()) }
    factory { GetPoiListUsecase(get(), get()) }
    factory { (view: PoiListView) -> PoiListPresenter(view, get()) }
    factory { (view: PoiDetailView, id: String) -> PoiDetailPresenter(view, id, get()) }
}

val modules = listOf(appModule, poiModule)