package com.mobilesword.resimlisozler.data

import com.mobilesword.resimlisozler.models.Categories
import com.mobilesword.resimlisozler.models.Images
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("kategoriler.json")
    suspend fun getCategories(): List<Categories>

    @GET("images/{filename}/resimler.json")
    suspend fun getImages(@Path("filename") query: String): List<Images>
}