package ru.akinadude.research

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_simple_coroutines_button.setOnClickListener {
            startActivity(Intent(this, SimpleCoroutinesActivity::class.java))
        }

        start_download_blocking_button.setOnClickListener {
            startActivity(Intent(this, DownloadBlockingActivity::class.java))
        }

        Log.d("TAG", "Start")

        /*launch {
            delay(1000) // it doesn't block a thread, but only suspends the coroutine itself.
            Log.d("TAG", "Hello")
        }

        runBlocking {
            delay(1000)
        }

        Thread.sleep(2000)
        Log.d("TAG", "Stop")

        // --
        val deferred = (1..1_000_000).map { n ->
            async {
                n
            }
        }
        runBlocking {
            val sum = deferred.sumBy { it.await() }
            println("Sum: $sum")
        }*/

        /*runBlocking {
            launch (Dispatchers.Main) {
                delay(200L)
                println("OLOLO, Task from runBlocking")
            }
        }*/

        /*runBlocking { // this: CoroutineScope
            launch {
                delay(200L)
                println("OLOLO, Task from runBlocking")
            }

            coroutineScope { // Creates a new coroutine scope
                launch {
                    delay(500L)
                    println("OLOLO, Task from nested launch")
                }

                delay(100L)
                println("OLOLO, Task from coroutine scope") // This line will be printed before nested launch
            }

            println("OLOLO, Coroutine scope is over") // This line is not printed until nested launch completes
        }*/

        /*
        Есть классная возможность добавлять оператором + в контекст ссылку на родительскую корутину.

        Не понимаю, как происходит отмена всех корутин в данном контексте, которая инитится вызовом
        job.cancel() в методе Activity.destroy()
        */
        /*runBlocking {
            val activity = Activity()
            activity.create()
            activity.doSomething()
            println("Launched coroutines")
            delay(500L)
            println("Destroying activity!")
            activity.destroy() // cancels all coroutines
            delay(1000) // visually confirm that they don't work
        }*/
    }
}

class Activity : CoroutineScope {
    lateinit var job: Job

    fun create() {
        job = Job()
    }

    fun destroy() {
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    fun doSomething() {
        //todo unconscious syntax inside
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught original $exception")
        }

        // launch ten coroutines for a demo, each working for a different time
        repeat(10) { i ->
            launch {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                println("Coroutine $i is done")
            }
        }
    }
}

/*
В раздел "Использование"
- launch и его аргументы
- другие методы, принимающие лямбды

Вопросы:
1. Как работает suspend метод delay без блокирования потока?
2.
*/

/*Иерархия скоупов

object GlobalScope : CoroutineScope

метод public suspend fun <R> coroutineScope(block: suspend CoroutineScope.() -> R): R
в файле CoroutineScope.kt

метод supervisorScope, что делает, зачем нужен?

interface CoroutineScope содержит public val coroutineContext: CoroutineContext
Дока по CoroutineScope'у:
https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html

Есть корутин-билдеры и есть scoping functions (coroutineScope, withContext)
билдеры launch и async это extension функции на CoroutineScope'е

Зачем нужен метод coroutineScope?

Для поддержки концепции structured concurrency
Если конкретнее, то для того, чтобы ограничить скоуп для операций, выполняемых в async'е.
Все async-корутины становятся дочерними по отношению к данному скоупу.
If the scope fails with an exception or is cancelled, all the children are cancelled, too.
suspend fun loadAndCombine(name1: String, name2: String): Image =
    coroutineScope {
        val deferred1 = async { loadImage(name1) }
        val deferred2 = async { loadImage(name2) }
        combineImages(deferred1.await(), deferred2.await())
    }

Взято из (1)
*/
