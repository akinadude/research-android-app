package ru.akinadude.research.api

import kotlinx.coroutines.Deferred
import ru.akinadude.research.model.github.User
import ru.akinadude.research.network.GithubFactory

class GithubApi {

    private val service = GithubFactory.createRetrofitService()

    fun getUser(username: String): Deferred<User> = service.getUser(username)
}