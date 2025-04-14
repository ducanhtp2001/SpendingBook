package com.example.appmoney.ui.categorytype

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.repository.Repository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatTypeViewModel() : ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    private val repository: Repository = Repository()

    private val _incomeList = MutableLiveData<List<Category>>()
    val incomeList: LiveData<List<Category>> = _incomeList

    private val _expList = MutableLiveData<List<Category>>()
    val expList: LiveData<List<Category>> = _expList

    private val _tabSelected = MutableLiveData<Int>()
    val tabSelected: LiveData<Int> get() = _tabSelected

    private val user = auth.currentUser

    fun getIncomeCat(onFailure: (String) -> Unit)= viewModelScope.launch(Dispatchers.IO) {
        repository.getCategory("Income", { list ->
            _incomeList.value = list
        }, onFailure)
    }

    fun getExpenditureCat(onFailure: (String) -> Unit) {
        repository.getCategory("Expenditure", { list ->
            _expList.value = list
        }, onFailure)
    }

    fun delCategory(
        typeId: String = "",
        idCat: String = "",
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        repository.deleteCategory(typeId, idCat, onSuccess, onFailure)
    }

    fun updateCategory(
        typeId: String,
        idCat: String,
        category: Category,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        repository.updateCategory(typeId, idCat, category, onSuccess, onFailure)
    }

//    fun loadIncome(){
//        val listCatIncome = mutableListOf<Category>()
//        user?.let {
//            db.collection("User").document(user.uid)
//                .collection("Category").document("Income")
//                .collection("Item")
//                .orderBy("timeCreate")
//                .get()
//                .addOnSuccessListener { result ->
//                    listCatIncome.clear()
//                    for (doc in result) {
//                        val data = doc.data
//                        val category = CategoryMap.toCategory(data).copy(idCat = doc.id)
//                        listCatIncome.add(category)
//                    }
//                    _incomeList.value = listCatIncome
//
//                }
//        }
//    }
//
//    fun loadExpenditure() = viewModelScope.launch {
//        user?.let {
//            db.collection("User").document(user.uid)
//                .collection("Category").document("Expenditure")
//                .collection("Item")
//                .orderBy("timeCreate")
//                .get()
//                .addOnSuccessListener { result ->
//                    val listCatExp = result.map { doc ->
//                        CategoryMap.toCategory(doc.data).copy(idCat = doc.id)
//                    }
//                    _expList.value = listCatExp
//                }
//        }
//    }
//
//    fun deleteCatIncome(idCat: String) = viewModelScope.launch {
//        user?.let {
//            db.collection("User").document(user.uid)
//                .collection("Category").document("Income")
//                .collection("Item").document(idCat)
//                .delete()
//                .addOnSuccessListener {
//                    loadIncome()
//                }
//        }
//
//    }
//
//    fun deleteCatExpenditure(idCat: String) {
//        user?.let {
//            db.collection("User").document(user.uid)
//                .collection("Category").document("Expenditure")
//                .collection("Item").document(idCat)
//                .delete()
//                .addOnSuccessListener {
//                    loadIncome()
//                }
//        }
//
//    }
//    fun getData(type:String, list: MutableLiveData<List<Category>>){
//        val user = auth.currentUser
//        user?.let {
//            db.collection("User").document(user.uid)
//                .collection("Category").document(type)
//                .collection("Item")
//                .orderBy("timeCreate")
//                .get()
//                .addOnSuccessListener { result ->
//                    val listCatExp = result.map { doc ->
//                        CategoryMap.toCategory(doc.data).copy(idCat = doc.id)
//                    }
//                    list.value = listCatExp
//                }
//        }
//    }


    fun setTabSelected(tab: Int) {
        Log.d("CatTabViewModel", "setTypeSelected: $tab")
        _tabSelected.value = tab
    }
}