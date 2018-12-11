package ru.akinadude.research.mvp.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.akinadude.research.mvp.view.BaseView
import kotlin.coroutines.CoroutineContext

abstract class LifecyclePresenter<out V : BaseView>(view: V)
    : BasePresenter<V>(view), CoroutineScope, LifecycleObserver {

    private lateinit var job: Job
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Logger.d("Coroutine exception caught: $throwable")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        job = Job()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = job.cancel()
}