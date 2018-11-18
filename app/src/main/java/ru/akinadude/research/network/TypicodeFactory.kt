package ru.akinadude.research.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TypicodeFactory {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    fun createRetrofitService(): TypicodeService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(TypicodeService::class.java)
}