package ru.akinadude.research.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GithubFactory {
    private const val BASE_URL = "https://api.github.com"

    fun createRetrofitService(): GithubService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GithubService::class.java)
}