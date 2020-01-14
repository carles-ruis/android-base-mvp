package com.carles.carleskotlin.common.ui

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.carles.carleskotlin.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Throwable.getMessageId() : Int = R.string.error_server_response

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}
