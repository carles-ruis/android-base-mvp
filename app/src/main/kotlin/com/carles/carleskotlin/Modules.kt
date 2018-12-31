package com.carles.carleskotlin

import android.preference.PreferenceManager
import com.carles.carleskotlin.poi.data.datasource.PoiCloudDatasource
import com.carles.carleskotlin.poi.data.datasource.PoiLocalDatasource
import com.carles.carleskotlin.poi.data.datasource.PoiService
import com.carles.carleskotlin.poi.repository.PoiRepository
import com.carles.carleskotlin.poi.ui.PoiDetailPresenter
import com.carles.carleskotlin.poi.ui.PoiDetailView
import com.carles.carleskotlin.poi.ui.PoiListPresenter
import com.carles.carleskotlin.poi.ui.PoiListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.single
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://t21services.herokuapp.com"
private fun providePoiService(retrofit: Retrofit) = retrofit.create(PoiService::class.java)

val appModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single("uiScheduler") { AndroidSchedulers.mainThread() }
    single("processScheduler") { Schedulers.io() }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

val poiModule = module {
    single { providePoiService(get()) }
    single { PoiLocalDatasource(get()) }
    single { PoiCloudDatasource(get(), get()) }
    single { PoiRepository(get(), get()) }
    factory { (view: PoiListView) -> PoiListPresenter(view, get("uiScheduler"), get("processScheduler"), get()) }
    factory { (view: PoiDetailView, id: String) -> PoiDetailPresenter(view, id, get("uiScheduler"), get("processScheduler"), get()) }
}

val modules = listOf(appModule, poiModule)