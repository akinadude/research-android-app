package ru.akinadude.research.manager

import kotlinx.coroutines.Deferred
import ru.akinadude.research.api.GithubApi
import ru.akinadude.research.model.github.UsersContainer

class SearchManager(private val api: GithubApi) {

    fun searchUsers(): Deferred<UsersContainer> = api.searchUsers()
}