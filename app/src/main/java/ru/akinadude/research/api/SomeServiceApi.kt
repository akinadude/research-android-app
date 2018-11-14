package ru.akinadude.research.api

import kotlinx.coroutines.CoroutineScope
import ru.akinadude.research.model.Post
import ru.akinadude.research.network.RetrofitFactory

class SomeServiceApi(private val coroutineScope: CoroutineScope) {

    private val service = RetrofitFactory.createRetrofitService()

    suspend fun getPosts(): List<Post> = service.getPosts2()
}