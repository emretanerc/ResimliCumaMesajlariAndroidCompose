package com.mobilesword.resimlisozler.data.repo

import com.mobilesword.resimlisozler.data.RetrofitInstance
import com.mobilesword.resimlisozler.models.Categories
import com.mobilesword.resimlisozler.models.Images


class DetailRepo {
    private val service = RetrofitInstance.getClient

    suspend fun getImages(fileName : String): List<Images> {
        return service.getImages(fileName)
    }
}