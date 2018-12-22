package ru.akinadude.research.mvp.presenter

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.model.github.UsersContainer
import ru.akinadude.research.mvp.view.SearchUsersView

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
                    val usersContainer = doPerformSearch(searchText).await()
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

    private fun isActive(searchJob: Job?): Boolean = searchJob?.isActive ?: false

    private fun doPerformSearch(searchText: String): Deferred<UsersContainer> = manager.searchUsersCo(searchText)

    private fun clear() {
        getView()?.showSearchUsers(emptyList())
    }
}