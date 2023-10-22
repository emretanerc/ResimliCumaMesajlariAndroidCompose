package com.mobilesword.resimlisozler.detailscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilesword.resimlisozler.data.repo.CategoriesRepo
import com.mobilesword.resimlisozler.data.repo.DetailRepo
import com.mobilesword.resimlisozler.models.Categories
import com.mobilesword.resimlisozler.models.Images
import kotlinx.coroutines.launch


class DetailViewModel : ViewModel() {
    private val repository = DetailRepo()

    private val _images = MutableLiveData<List<Images>>()
    val images: LiveData<List<Images>> = _images

    fun fetchImages(fileName:String) {
        viewModelScope.launch {
            try {
                val images = repository.getImages(fileName)
                _images.value = images
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

