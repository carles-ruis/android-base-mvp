package com.carles.carleskotlin.common.ui

import com.carles.carleskotlin.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Throwable.getMessageId() : Int = R.string.error_server_response

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}