package ru.akinadude.research.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import ru.akinadude.research.model.Post

interface RetrofitService {

    @GET("/posts")
    fun getPosts(): Deferred<Response<List<Post>>> //returns [ {}, {}, ... {} ]

    @GET("/posts")
    suspend fun getPosts2(): List<Post>
}