package ru.akinadude.research.mvp.presenter

import kotlinx.coroutines.launch
import ru.akinadude.research.manager.UserManager
import ru.akinadude.research.mvp.view.UserView

class UserPresenter(
        private val manager: UserManager,
        view: UserView
): LifeCyclePresenter<UserView>(view) {

    fun showUserInfo(username: String) = launch {
        getView()?.showProgress()
        try {
            val user = manager.getUser(username).await()
            getView()?.showUser(user)
        } catch (e: Exception) {
            getView()?.showError(e)
        } finally {
            getView()?.hideProgress()
        }
    }
}