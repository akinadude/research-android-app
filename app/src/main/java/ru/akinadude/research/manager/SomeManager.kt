package ru.akinadude.research.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import ru.akinadude.research.api.SomeServiceApi
import ru.akinadude.research.model.Post

class SomeManager(
        private val api: SomeServiceApi,
        private val scope: CoroutineScope
) {

    fun getPosts(): Deferred<List<Post>> = scope.async { api.getPosts() }
}