package ru.akinadude.research.mvp.presenter

import kotlinx.coroutines.launch
import ru.akinadude.research.manager.ProfileManager
import ru.akinadude.research.mvp.view.ProfileView

class ProfilePresenter(
        private val manager: ProfileManager,
        view: ProfileView
): LifecyclePresenter<ProfileView>(view) {

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