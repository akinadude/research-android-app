package ru.akinadude.research.ui.fragment.demo1

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_threads_and_co.*
import kotlinx.coroutines.*
import ru.akinadude.research.R
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

/**
 * Threads ans coroutines comparison
 *
 * */
class ThreadsVsCoFragment : Fragment(), CoroutineScope {

    companion object {

        fun newInstance(): ThreadsVsCoFragment {
            return ThreadsVsCoFragment()
        }
    }

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_threads_and_co, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        threads_start_btn.setOnClickListener {
            launch100kThreads()
        }

        co_start_btn.setOnClickListener {
            launch100kCoroutines()
        }
    }

    private fun launch100kThreads() {
        //managed to get OOM only with 100_000_000 threads ;)
        val jobs = List(100_000) {
            thread {
                Thread.sleep(1000)
                Logger.d(".")
            }
        }
        jobs.forEach { it.join() }
    }

    private fun launch100kCoroutines() = runBlocking {
        val jobs = List(100_000) {
            launch {
                delay(1000)
                Logger.d(".")
            }
        }
        jobs.forEach { it.join() }
    }

    private fun performGlobalWork() {
        GlobalScope.launch {
            /* ... */
        }
    }
}