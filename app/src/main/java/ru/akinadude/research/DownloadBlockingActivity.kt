package ru.akinadude.research

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.experimental.*
import okhttp3.OkHttpClient
import okhttp3.Request


class DownloadBlockingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        floating_action_button_1.setOnClickListener {
            launch {
                text_view.text = ""
                val deferred = async(Dispatchers.IO) { downloadDataBlocking() }
                val data = deferred.await()
                Log.d("TAG", "data: $data")
                display(data)
            }
        }

        floating_action_button_2.setOnClickListener {
            launch {
                text_view.text = ""
                downloadDataAndDisplay()
            }
        }
    }

    private fun display(data: String) {
        text_view.text = data
    }

    private fun downloadDataBlocking(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build()

        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
    }

    private suspend fun downloadDataAndDisplay() = coroutineScope {
        val data = async(Dispatchers.IO) { downloadDataBlocking() }
        Log.d("TAG", "downloadDataAndDisplay, data: $data")

        // Это можно было бы обернуть в withContext(Dispatchers.Main) { ... }, но нет смысла, т.к.
        // мы уже в контексте UI, который тянем из BaseActivity
        val result = data.await()
        display(result)
    }
}