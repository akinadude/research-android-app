package ru.akinadude.research.mvp.presenter

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.test.withTestContext
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.UsersContainer
import ru.akinadude.research.mvp.view.SearchUsersView
import ru.akinadude.research.utils.switchMap
import ru.akinadude.research.utils.switchMap2

class SearchCoPresenter(
        private val manager: SearchManager,
        view: SearchUsersView
) : LifecyclePresenter<SearchUsersView>(view) {

    private var searchJob: Job? = null

    fun performSearch(searchText: String) {
        if (isActive(searchJob)) {
            searchJob?.cancel()
        }
        searchJob = launch {
            if (searchText.isNotEmpty()) {
                getView()?.showProgress()
                try {
                    delay(400)
                    val usersContainer = manager.searchUsersCo(searchText).await()
                    getView()?.showSearchUsers(usersContainer.items)
                } catch (e: Exception) {
                    getView()?.showError(e)
                } finally {
                    getView()?.hideProgress()
                }
            } else {
                getView()?.hideProgress()
                clear()
            }
        }
    }

    fun performSearchWithChannel(searchText: String, channel: BroadcastChannel<String>) = launch {
        if (searchText.isNotEmpty()) {
            getView()?.showProgress()
            channel.send(searchText)
            delay(300)
        } else {
            getView()?.hideProgress()
            clear()
        }
    }

    fun produceSomething() = produce {
        var x = 1
        while (true) send(x++)
    }

    fun consumeSomething() = launch {
        val receiveChannel = produceSomething()
        receiveChannel.consumeEach { println(it) }
    }

    suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String = select<String> {
        a.onReceiveOrNull { value ->
            if (value == null)
                "Channel 'a' is closed"
            else
                "a -> '$value'"
        }
        b.onReceiveOrNull { value ->
            if (value == null)
                "Channel 'b' is closed"
            else
                "b -> '$value'"
        }
    }

    //todo how rx switchMap works?
    //todo openSubscription?

    fun subscribeToChannel(channel: BroadcastChannel<String>) = launch {
        /*val receiveChannel = channel.openSubscription()
        val receiveChannel2 = switchMap2(receiveChannel) { searchText ->
            manager.searchUsersCo(searchText)
        }
        receiveChannel2.consumeEach {
            try {
                getView()?.showSearchUsers(it.items)
            } catch (e: Exception) {
                getView()?.showError(e)
            } finally {
                getView()?.hideProgress()
            }
        }*/
        /*switchMap2(channel.openSubscription()) { searchText ->
            manager.searchUsersCo(searchText)
        }.consumeEach {
            try {
                getView()?.showSearchUsers(it.items)
            } catch (e: Exception) {
                getView()?.showError(e)
            } finally {
                getView()?.hideProgress()
            }
        }*/

        switchMap(channel.openSubscription()) { searchText ->
            produce<UsersContainer> { manager.searchUsersCo(searchText) }
        }.consumeEach {
            try {
                getView()?.showSearchUsers(it.items)
            } catch (e: Exception) {
                getView()?.showError(e)
            } finally {
                getView()?.hideProgress()
            }
        }
    }

    /*.switchMap(this, coroutineContext) { searchText ->
                    //manager.searchUsersCo(searchText).await()
                    val c = ConflatedBroadcastChannel<String>()
                    c.openSubscription()
                }*/

    /*channel.consumeEach { searchText ->
        try {
            delay(400)
            val usersContainer = manager.searchUsersCo(searchText).await()
            getView()?.showSearchUsers(usersContainer.items)
        } catch (e: Exception) {
            getView()?.showError(e)
        } finally {
            getView()?.hideProgress()
        }
    }*/

    //todo 1
    //init some big objects while playing splash screen animation
    //get data from database
    fun performSomeWork() = launch {
        //show loader

        withContext(Dispatchers.IO) {
            try {
                //get data
            } catch (t: Throwable) {
                //...
            } finally {
                //...
            }
        }

        //hide loader
        //populate UI
    }

    //todo 2 рассказ про отмену и structured concurrency
    //todo 3 привести пример неотменяемой корутины (без вызовов внутри suspended функций), сделать ее отменяемой (свойство isActive)
    //todo 4  finally {
    //        withContext(NonCancellable) {

    private fun isActive(searchJob: Job?): Boolean = searchJob?.isActive ?: false

    private fun clear() {
        getView()?.showSearchUsers(emptyList())
    }
}