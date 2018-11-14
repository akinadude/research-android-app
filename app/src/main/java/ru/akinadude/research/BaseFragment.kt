package ru.akinadude.research

import android.os.Bundle
import android.support.v4.app.Fragment
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Logger.d("Coroutine exception caught: $throwable")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Cancel job on activity destroy.
        // After destroy all children jobs will be cancelled automatically
        job.cancel()
    }
}