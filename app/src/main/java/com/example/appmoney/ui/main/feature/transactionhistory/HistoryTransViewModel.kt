package com.example.appmoney.ui.main.feature.transactionhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmoney.data.model.TransAndCat
import com.example.appmoney.data.repository.Repository

class HistoryTransViewModel: ViewModel() {

    private val repository = Repository()

    private val _listTranAndCat = MutableLiveData<List<TransAndCat>>()
    val listTransAndCat : LiveData<List<TransAndCat>> get() = _listTranAndCat

    fun getTrans(onFailure:(String)->Unit){
        repository.getTransWithCat(onSuccess = { list ->
            _listTranAndCat.value = list
        }, onFailure)
    }
}