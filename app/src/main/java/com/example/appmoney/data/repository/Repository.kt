package com.example.appmoney.data.repository

import com.example.appmoney.helper.CategoryMap
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.Transaction
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Repository {
    private val db = Firebase.firestore
    private val auth= Firebase.auth

    private fun getUserId():String{
        return auth.currentUser?.uid ?: throw Exception("User not logged in")
    }

    fun addCategory(typeId: String,category: Category, onSuccess: ()-> Unit, onFailure: (String)-> Unit){
        val userId = getUserId()
        val itemId = typeId + System.currentTimeMillis()
        db.collection("User").document(userId)
            .collection("Category").document(typeId)
            .collection("Item").document(itemId)
            .set(CategoryMap.toMap(category))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }
    fun getCategory(typeId: String, onSuccess: (List<Category>) -> Unit, onFailure: (String) -> Unit){
        val userId = getUserId()
        db.collection("User").document(userId)
            .collection("Category").document(typeId)
            .collection("Item")
            .get()
            .addOnSuccessListener { result->
                val list = result.map { doc->
                    val category = CategoryMap.toCategory(doc.data)
                    category.copy(idCat = doc.id)
                }
                onSuccess(list)
            }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }

    fun updateCategory(typeId: String, itemId: String,category: Category,onSuccess: () -> Unit,onFailure: (String) -> Unit){
        val userId = getUserId()
        db.collection("User").document(userId)
            .collection("Category").document(typeId)
            .collection("Item").document(itemId)
            .set(CategoryMap.toMap(category))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }
    fun deleteCategory(typeId: String, itemId: String,onSuccess: () -> Unit,onFailure: (String) -> Unit){
        val userId = getUserId()
        db.collection("User").document(userId)
            .collection("Category").document(typeId)
            .collection("Item").document(itemId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }
    fun addTrans(trans: Transaction, onSuccess: () -> Unit, onFailure: (String) -> Unit){
        val userId = getUserId()
        val itemId = "Trans" + System.currentTimeMillis()
        db.collection("User").document(userId)
            .collection("Transaction").document(itemId)
            .set(CategoryMap.toMapTrans(trans))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }
    fun updateTrans(idTrans: String,trans: Transaction, onSuccess: () -> Unit, onFailure: (String) -> Unit){
        val userId = getUserId()
        db.collection("User").document(userId)
            .collection("Transaction").document(idTrans)
            .set(CategoryMap.toMapTrans(trans))
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }
    fun delTrans(idTrans: String,trans: Transaction, onSuccess: () -> Unit, onFailure: (String) -> Unit){
        val userId = getUserId()
        db.collection("User").document(userId)
            .collection("Transaction").document(idTrans)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{onFailure(it.message ?: "Unknown error")}
    }
}