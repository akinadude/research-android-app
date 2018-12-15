package ru.akinadude.research.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import ru.akinadude.research.model.github.User
import ru.akinadude.research.model.github.UsersContainer

interface GithubService {

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Deferred<User>

    @GET("/search/users?q=+location:russia")
    fun searchUsers(): Deferred<UsersContainer>
}