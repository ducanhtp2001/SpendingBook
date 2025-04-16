package com.example.appmoney.data.repository

import android.util.Log
import com.example.appmoney.ui.common.helper.CategoryMap
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.TransAndCat
import com.example.appmoney.data.model.Transaction
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.QuerySnapshot
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

    fun getTransWithCat(
        onSuccess: (List<TransAndCat>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userId = getUserId()
        val categoryMap = mutableMapOf<String, Category>()
        val categoryTasks = mutableListOf<Task<QuerySnapshot>>()
        val catRef = db.collection("User").document(userId).collection("Category")

        catRef.get()
            .addOnSuccessListener { typeCats ->
                for (typeCat in typeCats) {
                    val task = catRef.document(typeCat.id).collection("Item").get()
                    categoryTasks.add(task)
                }

                // Khi tất cả các category item đã được lấy xong
                Tasks.whenAllSuccess<QuerySnapshot>(categoryTasks)
                    .addOnSuccessListener { results ->
                        for (snapshot in results) {
                            for (doc in snapshot) {
                                val cat = CategoryMap.toCategory(doc.data).copy(idCat = doc.id)
                                categoryMap[doc.id] = cat
                            }
                        }

                        // Lúc này mới lấy transaction
                        db.collection("User").document(userId)
                            .collection("Transaction")
                            .get()
                            .addOnSuccessListener { result ->
                                val list = mutableListOf<TransAndCat>()
                                result.forEach { doc ->
                                    val trans = CategoryMap.toStringTrans(doc.data).copy(id = doc.id)
                                    val matchCat = categoryMap[trans.categoryId]
                                    if (matchCat != null) {
                                        list.add(TransAndCat(matchCat, trans))
                                    } else {
                                        Log.w("Repository", "Không tìm thấy category cho id = ${trans.categoryId}")
                                    }
                                }
                                Log.d("Repository", "Fetched ${list.size} TransAndCat")
                                onSuccess(list)
                            }
                            .addOnFailureListener {
                                onFailure(it.message ?: "Lỗi khi lấy Transaction")
                            }

                    }
                    .addOnFailureListener {
                        onFailure(it.message ?: "Lỗi khi lấy các category item")
                    }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Lỗi khi lấy collection Category")
            }
    }

}