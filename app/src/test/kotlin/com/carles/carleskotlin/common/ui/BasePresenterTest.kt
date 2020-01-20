package com.carles.carleskotlin.common.ui

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.reactivex.disposables.Disposable
import org.junit.Assert.*
import org.junit.Test

class BasePresenterTest {

    val presenter = object : BasePresenter<BaseView>(mockk()) {}

    @Test
    fun onViewDestroyed_shouldDispose() {
        mockk<Disposable>(relaxed = true).addTo(presenter.disposables)
        presenter.onViewDestroyed()
        assertTrue(presenter.disposables.isDisposed)
    }

    @Test
    fun addDisposable_shouldAdd() {
        mockk<Disposable>().addTo(presenter.disposables)
        assertEquals(1, presenter.disposables.size())
        assertFalse(presenter.disposables.isDisposed)
    }
}