package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ScopedDemoFragment : Fragment(), CoroutineScope {

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

    override fun onStart() {
        super.onStart()

        runBlocking {
            val jobs = List(100_000) {
                launch {
                    delay(1000)
                    print(".")
                }
            }
            jobs.forEach { it.join() }
        }
    }

    private fun performGlobalWork() {
        GlobalScope.launch {
            /* ... */
        }
    }

    private fun performGlobalWorkExplicitParams() {
        GlobalScope.launch(
                Dispatchers.Main +
                        Job() +
                        CoroutineName("HailCoroutine!") +
                        CoroutineExceptionHandler { _, _ -> /* ... */ }
        ) {
            /* ... */
        }
    }

    private fun performWork() {
        launch {
            // code will be performed using context of received scope.
        }
    }

    private fun performMoreWork() {
        val parentJob = Job()
        val deferred = async(Dispatchers.IO + parentJob) {
            // code will be performed using parameter context which consists of two items.
        }
        launch {
            val value = deferred.await()
            println("Value from deferred: $value")
        }
    }

    fun performAsyncWork() = launch {
        //show loader

        withContext(Dispatchers.IO) {
            try {
                //get data
            } catch (t: Throwable) {
                //handle error
            } finally {
                //release resources
            }
        }

        //hide loader
        //populate UI
    }
}