package com.example.appmoney.ui.main.feature.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmoney.data.model.Category
import com.example.appmoney.ui.common.helper.TabObject

class InputViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories: LiveData<List<Category>> = _categories

    private val _selectedTab = MutableLiveData<Int>()
    val selectedTab: LiveData<Int> = _selectedTab

    fun setTab(tab: Int) {
        _selectedTab.value = tab

        TabObject.changeTab(tab)
    }

    fun onSelectedCategory(category: Category) {
        _categories.value?.let { currentCategories ->
            val newList = currentCategories.map { categoryOnList ->
                if (categoryOnList == category) {
                    categoryOnList.copy(isSelected = true)
                } else {
                    categoryOnList.copy(isSelected = false)
                }
            }
            _categories.value = newList
        }
    }

    fun init(categories: List<Category>) {
        _categories.value = categories
    }
}