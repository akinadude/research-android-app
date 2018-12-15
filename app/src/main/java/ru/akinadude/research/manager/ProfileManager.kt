package ru.akinadude.research.manager

import kotlinx.coroutines.Deferred
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.model.github.User

class ProfileManager(private val api: GithubApi) {

    fun getUser(username: String): Deferred<User> = api.getUser(username)
}