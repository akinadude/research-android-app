package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.CoroutineContext

class ScopedDemoFragment : Fragment(), CoroutineScope {

    /*todo Упомянуть в презентации
    Callback hell
    Пару слайдов про то, что такое скоуп и контекст корутины
    * */

    /*todo generate examples
    Doing...Пример в коде, когда корутина блокирует UI поток

    Doing... Пример с созданием 100k потоков и 100k корутин

    Doing... Последовательное и параллельное выполнение (два примера с await из статьи Данилюка)
    - пример со одним запросом
    - пример с мержем двух вытянутых из сети картинок или двух запросов

    Done! Пример с тремя корутинами, работающими на main треде и не блокирующими его.

    Пример с GlobalScope'ом. Сказать про ограничение скоупа

    ----------------------------------------------
    Конец!

    Использование оператора [] для смены диспетчера, работы в бэкграунде и возврате в main тред.
    * */
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

    private fun coBlockingThread() {
        launch {
            val deferred = async {
                downloadData()
            }
            val data = deferred.await()
            Logger.d("Data: $data")
        }
    }

    private fun downloadData(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build()

        val response = client.newCall(request).execute()
        return response.body()?.string() ?: ""
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