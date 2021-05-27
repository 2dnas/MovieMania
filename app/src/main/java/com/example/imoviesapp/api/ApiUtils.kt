package com.example.imoviesapp.api

import retrofit2.Retrofit

object ApiUtils {

    private const val BASE_URL = "https://api.unreel.me/v2/sites/popcornflix/"

    val apiService : ApiService?
    get () = RetrofitClient.getClient(BASE_URL)?.create(ApiService::class.java)
}