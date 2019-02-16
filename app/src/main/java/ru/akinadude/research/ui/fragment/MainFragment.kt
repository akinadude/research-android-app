package ru.akinadude.research.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import ru.akinadude.research.R
import ru.akinadude.research.mvp.presenter.MainPresenter
import ru.akinadude.research.mvp.view.MainView
import ru.akinadude.research.ui.fragment.demo1.AsyncCoFragment
import ru.akinadude.research.ui.fragment.demo1.RaceCoFragment
import ru.akinadude.research.ui.fragment.demo1.ThreadsVsCoFragment

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

        start_async_screen_button.setOnClickListener {
            presenter.navigateToAsyncScreen()
        }

        start_threads_vs_co_screen_button.setOnClickListener {
            presenter.navigateToThreadsVsCoScreen()
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

    override fun navigateToAsyncScreen() {
        fragmentManager?.let { fm ->
            val f = AsyncCoFragment.newInstance()
            fm.beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .addToBackStack(f.javaClass.name)
                    .commit()
        }
    }

    override fun navigateToThreadsVsCoScreen() {
        fragmentManager?.let { fm ->
            val f = ThreadsVsCoFragment.newInstance()
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
}