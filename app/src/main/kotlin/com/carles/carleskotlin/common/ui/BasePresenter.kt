package com.carles.carleskotlin.common.ui

import com.carles.carleskotlin.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Throwable.getMessageId() : Int = R.string.error_server_response

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    internal val disposables = CompositeDisposable()

    open fun onViewCreated() {}

    open fun onViewDestroyed() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    internal fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}