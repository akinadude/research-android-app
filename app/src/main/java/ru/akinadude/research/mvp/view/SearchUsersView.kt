package ru.akinadude.research.mvp.view

import ru.akinadude.research.model.github.User

interface SearchUsersView: BaseView {
    fun showProgress()
    fun hideProgress()
    fun showSearchUsers(users: List<User>)
    fun showError(t: Throwable)
}