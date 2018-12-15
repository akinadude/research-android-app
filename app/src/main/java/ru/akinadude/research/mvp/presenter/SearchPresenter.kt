package ru.akinadude.research.mvp.presenter

import kotlinx.coroutines.launch
import ru.akinadude.research.manager.SearchManager
import ru.akinadude.research.mvp.view.SearchUsersView

class SearchPresenter(
        private val manager: SearchManager,
        view: SearchUsersView
): LifecyclePresenter<SearchUsersView>(view) {

    fun showUsers() = launch {
        getView()?.showProgress()
        try {
            val users = manager.searchUsers().await()
            getView()?.showSearchUsers(users.items)
        } catch (e: Exception) {
            getView()?.showError(e)
        } finally {
            getView()?.hideProgress()
        }
    }
}