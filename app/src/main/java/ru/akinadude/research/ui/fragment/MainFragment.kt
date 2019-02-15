package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import ru.akinadude.research.R
import ru.akinadude.research.mvp.presenter.MainPresenter
import ru.akinadude.research.mvp.view.MainView

class MainFragment : BaseCoFragment(), MainView {

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val presenter: MainPresenter = MainPresenter(this)

    override val lifecyclePresenter
        get() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_first_screen_button.setOnClickListener {
            presenter.navigateToFirstScreen()
        }

        start_second_screen_button.setOnClickListener {
            presenter.navigateToSecondScreen()
        }

        start_race_screen_button.setOnClickListener {
            presenter.navigateToRaceScreen()
        }
    }

    override fun navigateToFirstScreen() {
        fragmentManager?.let { fm ->
            val f = SearchUsersFragment.newInstance()
            fm.beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .addToBackStack(f.javaClass.name)
                    .commit()
        }
    }

    override fun navigateToSecondScreen() {
        fragmentManager?.let { fm ->
            val f = SearchUsersCoFragment.newInstance()
            fm.beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .addToBackStack(f.javaClass.name)
                    .commit()
        }
    }

    override fun navigateToRaceScreen() {
        fragmentManager?.let { fm ->
            val f = RaceCoFragment.newInstance()
            fm.beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .addToBackStack(f.javaClass.name)
                    .commit()
        }
    }

    //===========================
    // Really need to do
    //===========================
    //todo придумать и реализовать на корутинах сложные кейсы комбинирования данных.
    // Можно подумать на предмет
    // - вытянуть все репозитории юзера
    // - вытянуть количество коммитов по каждому репозиторию юзера
    // - количество добавленных/удаленных/измененных пользователем строк
    // - поиск по репозиториям пользователя

    //todo реализовать несколько операторов из rx на корутинах
    //попробовать без каналов
    //попробовать с каналами

    //todo cold и hot обзерваблы в терминах корутин. Как имплементить? Каналы?

    // Представить решение с помощью rx и с помощью корутин. Сравнить, обозначить преимущества и недостатки.
    // Понадобится хорошее понимание rx (операторы, паблишеры, back pressure)

    //todo поискать интересные решения, основанные на корутинах.
    // Как их можно применить к андроид-приложению.

    //todo
    // Тестирование
    // - подменять контекст
    // - мокировать корутину, Mockk

    //todo
    // Потенциальные проблемы
    // - как решается проблема unhandled global exception, проявляющаяся при использовании rx?
    // - ...
    //===========================

    //Cicerone library
    //Create apply repository pattern
    //clean arch: presetner interactor repository
    //RIB arch (Uber)

    /*
    todo напиать руководство, как работать с отменой корутин и с exception'aми с примерами кода.
    соединить главу Exception handling с главой, где ранее упоминался CancellationException
    почитать доку к классам
    Cancellation is tightly bound with exceptions.
    Cancelling without a cause is a mechanism for parent to cancel its children without cancelling itself.

    todo придумать разные кейсы работы с данными, где хорошо работает rx с его операторами.
    перезапрос токена, если текущий невалиден для запроса, требующего авторизацию
    Можно подсмотреть у Docdoc.
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

    fun searchUsers(token: AuthToken): Call<List<User>> -> suspend fun searchUsers(token: AuthToken): List<User>

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