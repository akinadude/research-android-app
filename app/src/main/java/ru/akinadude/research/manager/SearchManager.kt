package ru.akinadude.research.manager

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.model.github.UsersContainer

class SearchManager(private val api: GithubApi) {

    fun searchUsers(searchText: String): Single<UsersContainer> = api.searchUsers(searchText)

    fun searchUsersCo(searchText: String): Deferred<UsersContainer> = api.searchUsersCo(searchText)
}