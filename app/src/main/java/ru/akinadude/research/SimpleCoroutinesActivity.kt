package ru.akinadude.research

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import ru.akinadude.research.network.RetrofitFactory
import kotlin.coroutines.coroutineContext

class SimpleCoroutinesActivity : BaseActivity() {

    //todo напиать руководство, как работать с отменой корутин и с exception'aми с примерами кода.
    // соединить главу Exception handling с главой, где ранее упоминался CancellationException
    // почитать доку к классам
    // Cancellation is tightly bound with exceptions.
    // Cancelling without cause is a mechanism for parent to cancel its children without cancelling itself.

    //todo послать и обаботать retrofit'овый запрос (instagram, flickr)
    // переключение потоков
    // обложить этот флоу MVP, сделать обработку ошибок

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        floating_action_button_1.setOnClickListener {
            //setTextAfterDelay(2, "Hello from a coroutine!")

            val service = RetrofitFactory.createRetrofitService()
            launch {
                /*val handler = CoroutineExceptionHandler { _, exception ->
                    println("Caught $exception with suppressed ${exception.suppressed.contentToString()}")
                }*/
                /*try {
                    val response = service.getPosts().await()
                    Logger.d("Retrofit response posts ${response.posts}")
                } catch (e: Exception) {
                    Logger.d("Caught $e")
                } finally {
                    Logger.d("Finally block")
                }*/
                val response = service.getPosts().await()
                if (response.isSuccessful) {
                    Logger.d("Retrofit response ${response.body()}")
                } else {
                    Logger.d("Retrofit error ${response.code()}")
                }

                /*
                Заметки по статье
                1)

                2) Запуск корутины с таймаутом
                withTimeout()
                withTimeoutOrNull()

                3) Example of lifecycle aware coroutine scope for LifecycleObserver

                4) CoroutineExceptionHanbler usage
                */
            }
        }
    }

    private fun setTextAfterDelay(seconds: Long, text: String) {
        GlobalScope.launch(Dispatchers.Main) {
            Logger.d("Show loading")

            val result = withContext(Dispatchers.IO) {
                Logger.d("Loading data in background start")
                delay(seconds * 1000)
                Logger.d("Loading data in background end")
            }

            Logger.d("Show data in UI")
        }
    }

    /*
    todo Сделать ретрофитовый запрос и обработать результат
    //done! простенький пример
    // перевести на корутины один запрос в Docdoc app'е

    todo Обернуть это в MVP

    todo Обработать exception'ы

    todo написать тесты на manager и на presenter

    todo rx операторы. Работа с Single'ом и Publisher'ами.
    // почитать, устранить пробелы в знаниях

    todo как будет выгдлядеть запрос, требующий валидного авторизационного токена (в коде Docdoc)
    при использовании корутин
    */
/*
    class LocalData(val name: String)
    class Data(val name: String)
    class AuthToken(val token: String)
    val token: AuthToken = AuthToken("token")

    suspend fun getAuthToken(): AuthToken = token

    suspend fun sendData(token: AuthToken, data: LocalData): Data = Data("dataFromBackend")

    suspend fun updateData(data: Data) {
    }

    suspend fun postData(localData: LocalData) {
        val token = getAuthToken()
        val data = sendData(token, localData)
        updateData(data)
    }*/

    //Похоже на блокирующие вызовы!

    /*
    What is suspending functions?
    Модификатор говорит о том, что метод, который с ним объявлен, может на некоторое время приостановить
    свое выполнение и возобновить его позже при этом не блокируя поток, на котором происходит исполнение.

    fun getUsers(token: AuthToken): Call<List<User>> -> suspend fun getUsers(token: AuthToken): List<User>

    =========================
    Ограничения, связанные с модификатором suspended

    That is because delay is a special suspending function that does not block a thread,
    but suspends coroutine and it can be only used from a coroutine.


    launch { // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay
        println("World!")
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep app alive

    Попробуем заменить launch на thread и посмотрим, что будет при запуске.

    Будет ошибка компиляции:
    Error: Kotlin: Suspend functions are only allowed to be called from a coroutine or another suspend function

    =========================
    ...

    */
}