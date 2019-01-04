package ru.akinadude.research.utils

import io.reactivex.disposables.Disposable
import ru.akinadude.research.mvp.presenter.IDisposable

fun Disposable.disposeBy(presenter: IDisposable): Disposable {
    presenter.addDisposable(this)
    return this
}