package ru.akinadude.research.ui.activity

import android.os.Bundle
import android.util.Log
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.akinadude.research.R


class DownloadBlockingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        floating_action_button_1.setOnClickListener { getAndShowData() }

        floating_action_button_2.setOnClickListener { getAndShowDataTheSameCoroutine() }
    }

    private fun getAndShowData() = launch {
        clear()
        val deferred = async(Dispatchers.IO) { downloadData() }
        val data = deferred.await()
        Logger.d("Data: $data")
        display(data)
    }

    // Don't launch another coroutine, use existed one with modified context
    private fun getAndShowDataTheSameCoroutine() = launch {
        clear()
        val data = withContext(Dispatchers.IO) {
            downloadData()
        }
        Logger.d("Data: $data")
        display(data)
    }

    private fun clear() {
        text_view.text = ""
    }

    private fun display(data: String) {
        text_view.text = data
    }

    private fun downloadData(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build()

        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
    }

    private suspend fun downloadDataAndDisplay() = coroutineScope {
        //val job = this.coroutineContext[Job]
        val data = async(Dispatchers.IO) { downloadData() }
        Log.d("TAG", "downloadDataAndDisplay, data: $data")

        // Это можно было бы обернуть в withContext(Dispatchers.Main) { ... }, но нет смысла, т.к.
        // мы уже в контексте UI, который тянем из BaseActivity
        withContext(Dispatchers.Main) {
            val result = data.await()
            display(result)
        }
    }
}