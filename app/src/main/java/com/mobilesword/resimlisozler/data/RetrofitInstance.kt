package com.mobilesword.resimlisozler.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://etcmobileapps.com/resimlicumamesajlari/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getClient: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}