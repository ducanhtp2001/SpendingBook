package com.example.appmoney.data.model

import java.io.Serializable

data class Transaction(
    val id :String = "",
    val amount: Long = 0,
    val date: String = "",
    val note: String = "",
    val categoryId : String="",
    val typeTrans: String= "Income"
): Serializable {}

data class TransAndCat(
    val category: Category,
    val trans: Transaction
): Serializable {}