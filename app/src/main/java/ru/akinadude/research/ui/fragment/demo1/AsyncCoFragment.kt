package ru.akinadude.research.ui.fragment.demo1

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_async_co.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.akinadude.research.R
import kotlin.coroutines.CoroutineContext

class AsyncCoFragment : Fragment(), CoroutineScope {

    companion object {

        fun newInstance(): AsyncCoFragment {
            return AsyncCoFragment()
        }
    }

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val baseUrl = "https://jsonplaceholder.typicode.com/"
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_async_co, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async_co_start_btn.setOnClickListener {
            requestPostsWithLaunch()
            //requestPostsWithAsync()
            //requestTwoListsOfEntitiesInParallel()
        }
    }

    private fun requestPostsWithLaunch() = launch {
        clearUI()
        val posts = fetch("posts") //crash with NetworkOnMainThreadException
        displayInUI(posts)
    }

    private fun requestPostsWithAsync() = launch {
        clearUI()
        val deferred = async { //crash with NetworkOnMainThreadException without changing the dispatcher
            fetch("posts")
        }
        val posts = deferred.await()
        displayInUI(posts)
    }

    private fun requestTwoListsOfEntitiesInParallel() = launch {
        clearUI()

        val deferred1 = async(Dispatchers.IO) {
            fetch("posts")
        }

        val deferred2 = async(Dispatchers.IO) {
            fetch("users")
        }

        val result = deferred1.await() + deferred2.await()
        displayInUI(result)
    }

    private fun fetch(what: String): String {
        val request = Request.Builder()
                .url(baseUrl + what)
                .build()

        //network call
        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
    }

    private fun clearUI() {
        async_co_text_view.text = ""
    }

    private fun displayInUI(data: String) {
        async_co_text_view.text = data
    }
}