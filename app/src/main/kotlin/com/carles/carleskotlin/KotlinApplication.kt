package com.carles.carleskotlin

import android.app.Application
import io.realm.Realm
import org.koin.android.ext.android.startKoin

class KotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules)
        Realm.init(this)
    }
}