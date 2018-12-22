package ru.akinadude.research.mvp.presenter

import io.reactivex.disposables.Disposable

interface IDisposable {
    fun addDisposable(disposable: Disposable)
}