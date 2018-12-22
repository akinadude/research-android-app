package ru.akinadude.research.api

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import ru.akinadude.research.model.github.User
import ru.akinadude.research.model.github.UsersContainer
import ru.akinadude.research.network.GithubFactory

class GithubApi {

    private val service = GithubFactory.createRetrofitService()

    fun getUser(username: String): Deferred<User> = service.getUser(username)

    fun searchUsersCo(searchText: String): Deferred<UsersContainer> = service.searchUsersCo(searchText)

    fun searchUsers(searchText: String): Single<UsersContainer> = service.searchUsers(searchText)
}