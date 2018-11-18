package ru.akinadude.research.api

import ru.akinadude.research.model.Post
import ru.akinadude.research.network.TypicodeFactory

class SomeServiceApi {

    private val service = TypicodeFactory.createRetrofitService()

    suspend fun getPosts(): List<Post> = service.getPosts2()
}