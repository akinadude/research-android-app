package ru.akinadude.research.mvp.view

import ru.akinadude.research.model.github.User

interface SearchUsersView: BaseView {
    fun showProgress()
    fun hideProgress()
    fun showSearchUsers(users: List<User>)//todo it can be unified by creating base class for all models
    fun showError(e: Exception)
}