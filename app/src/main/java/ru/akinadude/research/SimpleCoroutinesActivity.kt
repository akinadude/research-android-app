package ru.akinadude.research

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.experimental.*

class SimpleCoroutinesActivity : BaseActivity() {

    //done [27.10] craft xml for UI

    //done [27.10] Why AS is shouting at coroutine builders
    // Есть рекомендация имплементить интерфейс CoroutineScope в расках той сущности, у кот. есть lifecycle.
    // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html

    //done [27.10] послать и обаботать запрос в сеть
    // переключение потоков

    //todo читать доку на офф сайте (+2 главы)
    // Exception Handling
    // Select Expression

    // todo составить план для части lite
    // посмотреть презенташки (4h)

    //todo
    // look through the sources, how is adding parent job to context with + operator and cancelling works?
    // ask the question about plus operator, composable context and cancelling approach

    //todo Синтаксис метода CoroutineScope.isActive
    // интересует вот это coroutineContext[Job]

    //todo разобраться с (написать короткое объяснение по каждому из пунктов)
    // 1) корутин-билдерами
    // 2) скоупом корутин
    // 3) контекстом корутин
    // 4) со structured concurrency
    // 5) сформулировать вопрос про наличествующий у контекста/скоупа his own Job

    //todo послать и обаботать retrofit'овый запрос (instagram, flickr)
    // переключение потоков
    // обложить этот флоу MVP, сделать обработку ошибок

    /*todo
    Articles
    1. https://proandroiddev.com/android-coroutine-recipes-33467a4302e9
    2. https://geoffreymetais.github.io/code/coroutines/
    3. https://medium.com/@andrea.bresolin/playing-with-kotlin-in-android-coroutines-and-how-to-get-rid-of-the-callback-hell-a96e817c108b

    Read already
    1. [28.10] https://medium.com/@elizarov/structured-concurrency-722d765aa952
        Хорошее объяснение от Елизарова про
        1) необходимость имплементить интерфейс CoroutineScope в классах, имеющих lifecycle.
        2) structured concurrency и метод coroutineScope {...}

    2.
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        /*floating_action_button.setOnClickListener {
            launch(Dispatchers.Main) {
                setTextAfterDelay(2, "Hello from a coroutine!")
            }
        }*/


    }

    /*private suspend fun setTextAfterDelay(seconds: Long, text: String) {
        delay(seconds * 1000)
        text_view.text = text

        *//*runBlocking {
            launch(Dispatchers.Main) {
                //view.showLoading()

                val result = withContext(Dispatchers.IO) {
                    //load date
                }

                //view.showData(result)
            }
        }*//*
    }*/
}