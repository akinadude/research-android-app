package ru.akinadude.research.mvp.view

import ru.akinadude.research.model.github.User

interface ProfileView : BaseView{
    fun showProgress()
    fun hideProgress()
    fun showUser(user: User)
    fun showError(e: Exception)
}