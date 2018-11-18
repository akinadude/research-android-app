package ru.akinadude.research.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import ru.akinadude.research.model.Post

interface TypicodeService {

    @GET("/posts")
    fun getPosts(): Deferred<Response<List<Post>>> //returns [ {}, {}, ... {} ]

    //todo попробовать избавиться от тройной вложенности
    //Deferred<<List<Post>>

    @GET("/posts")
    suspend fun getPosts2(): List<Post>
}