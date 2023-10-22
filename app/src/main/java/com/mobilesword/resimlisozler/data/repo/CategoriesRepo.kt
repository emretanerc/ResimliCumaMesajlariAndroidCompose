package com.mobilesword.resimlisozler.data.repo

import com.mobilesword.resimlisozler.data.RetrofitInstance
import com.mobilesword.resimlisozler.models.Categories


class CategoriesRepo {
    private val service = RetrofitInstance.getClient

    suspend fun getCategories(): List<Categories> {
        return service.getCategories()
    }
}