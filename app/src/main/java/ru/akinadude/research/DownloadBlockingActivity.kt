package ru.akinadude.research

import android.os.Bundle
import android.util.Log
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request


class DownloadBlockingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        floating_action_button_1.setOnClickListener {
            launch {
                text_view.text = ""
                val deferred: Deferred<String> = async(Dispatchers.IO) { downloadData() }
                val data = deferred.await()
                Logger.d("Data: $data")
                display(data)
            }
        }

        floating_action_button_2.setOnClickListener {
        }
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