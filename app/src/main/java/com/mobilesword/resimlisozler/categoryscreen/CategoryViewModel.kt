package com.mobilesword.resimlisozler.categoryscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilesword.resimlisozler.data.repo.CategoriesRepo
import com.mobilesword.resimlisozler.models.Categories
import kotlinx.coroutines.launch


class CategoryViewModel : ViewModel() {
    private val repository = CategoriesRepo()

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>> = _categories

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categories = repository.getCategories()
                _categories.value = categories
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}