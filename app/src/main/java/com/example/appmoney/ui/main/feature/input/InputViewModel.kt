package com.example.appmoney.ui.main.feature.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.CategoryColor
import com.example.appmoney.data.model.Transaction
import com.example.appmoney.data.repository.Repository
import com.example.appmoney.ui.common.helper.TabObject

class InputViewModel : ViewModel() {
    private val repo = Repository()
    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories: LiveData<List<Category>> = _categories

    private val _selectedTab = MutableLiveData<Int>()
    val selectedTab: LiveData<Int> = _selectedTab

    private val _err = MutableLiveData<String?>()
    val err: LiveData<String?> = _err

    fun clearErr() {
        _err.value = null
    }

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

        fun getSelectedCat(): Category?{
            return categories.value?.firstOrNull{it.isSelected}
        }

    fun init(categories: List<Category>) {
        _categories.value = categories
    }
    fun addTrans (sDate: String,sAmount: Long,sNote: String,typeTrans: String, onSuccess: ()-> Unit, onFailure: (String)->Unit){

        val sCategoryId = getSelectedCat()?.idCat
        if (sAmount.toString().isEmpty()) {
            _err.value = "Bạn chưa nhập số tiền"
            return
        }
        if (sCategoryId == null){
            _err.value = "Bạn chưa chọn danh mục"
            return
        }
        val transaction = Transaction(
            date = sDate,
            amount = sAmount,
            note = sNote,
            categoryId = sCategoryId,
            typeTrans = typeTrans,
            )

        repo.addTrans(transaction,onSuccess,onFailure)
    }

}