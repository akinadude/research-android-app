package ru.akinadude.research.mvp.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.akinadude.research.mvp.view.BaseView
import kotlin.coroutines.CoroutineContext

abstract class LifeCyclePresenter<out V : BaseView>(view: V)
    : BasePresenter<V>(view), CoroutineScope, LifecycleObserver {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        job = Job()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = job.cancel()
}