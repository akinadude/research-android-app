package ru.akinadude.research.manager

import ru.akinadude.research.api.SomeServiceApi
import ru.akinadude.research.model.Post

class SomeManager(private val api: SomeServiceApi) {

    suspend fun getPosts(): List<Post> = api.getPosts()
}