package ru.akinadude.research.network

import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.akinadude.research.model.github.User
import ru.akinadude.research.model.github.UsersContainer

interface GithubService {

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Deferred<User>

    @GET("/search/users")
    fun searchUsersCo(@Query("q") searchText: String): Deferred<UsersContainer>

    @GET("/search/users")
    fun searchUsers(@Query("q") searchText: String): Single<UsersContainer>
}