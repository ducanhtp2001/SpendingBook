package com.example.appmoney.data.model

data class Transaction(
    val id :String = "",
    val amount: Long = 0,
    val date: String = "",
    val note: String = "",
    val categoryId : String="",
    val typeTrans: String=""
) {}

data class TransAndCat(
    val category: Category,
    val trans: Transaction
) {}