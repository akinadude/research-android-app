package ru.akinadude.research.mvp.presenter

import ru.akinadude.research.mvp.view.BaseView
import java.lang.ref.WeakReference


abstract class BasePresenter<out V: BaseView>(view: V) {
    private val viewRef: WeakReference<V> = WeakReference(view)

    internal fun getView(): V? = viewRef.get()

    open fun onDestroy() = viewRef.clear()
}
