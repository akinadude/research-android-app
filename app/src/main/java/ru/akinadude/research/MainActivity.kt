package ru.akinadude.research

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager?.let { fm ->
            val f = MainFragment.newInstance()
            fm.beginTransaction()
                    .add(R.id.fragment_container, f)
                    .commit()
        }

        Log.d("TAG", "Start")
    }

    override fun onBackPressed() {
        //todo handle back pressed within fragments

        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
            //todo can also be a call onBackPressed on a fragment instance.
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
