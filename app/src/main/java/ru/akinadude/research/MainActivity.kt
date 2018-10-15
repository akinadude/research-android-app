package ru.akinadude.research

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
