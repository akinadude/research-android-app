package ru.akinadude.research.mvp.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.akinadude.research.mvp.view.BaseView

abstract class BaseRxLifecyclePresenter<out T : BaseView>(view: T) :
        BasePresenter<T>(view),
        IDisposable {

    private lateinit var compositeDisposable: CompositeDisposable

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun onStart() {
        initDisposable()
    }

    fun onStop() {
        compositeDisposable.dispose()
    }

    private fun initDisposable() {
        if (!this::compositeDisposable.isInitialized || compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

}