package com.example.appmoney.helper

import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.CategoryColor
import com.example.appmoney.data.model.CategoryImage

object CategoryMap {

    fun toMap(category: Category):Map<String,Any>{
        return hashMapOf(
            "image" to category.image.name,
            "color" to category.color.name,
            "desCat" to category.desCat,
            "timeCreate" to category.timeCreate
        )
    }

    fun toCategory(categoryMap: Map<String,Any>): Category{
        return Category(
            idCat = (categoryMap["id"] as? String) ?: "",
            image = CategoryImage.valueOf((categoryMap["image"] as? String) ?: ""),
            color = CategoryColor.valueOf((categoryMap["color"] as? String) ?: ""),
            desCat = (categoryMap["desCat"] as? String) ?: "",
            timeCreate = (categoryMap["timeCreate"] as? Long) ?: 0
        )
    }
}