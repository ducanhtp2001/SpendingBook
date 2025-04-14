package com.example.appmoney.ui.mainScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.appmoney.R
import com.example.appmoney.ui.categorytype.CategoryTypeFragment
import com.example.appmoney.ui.different.MoreFragment
import com.example.appmoney.ui.edit.EditCategoryFragment
import com.example.appmoney.fixcategory.FixFragment
import com.example.appmoney.ui.input.InputFragment
import com.example.appmoney.ui.input.income.IncomeFragment
import com.example.appmoney.ui.report.ReportFragment
import com.example.appmoney.ui.transactionhistory.HistoryTransFragment

sealed interface AppScreen {
    data object Income: AppScreen
    data object Input: AppScreen
    data object Edit: AppScreen
    data object HistoryTrans: AppScreen
    data object Report: AppScreen
    data object More: AppScreen
    data object Category: AppScreen
    data object Fix:AppScreen

    fun getScreenInstance(): Fragment {
        return when(this) {
            HistoryTrans -> HistoryTransFragment()
            Edit -> EditCategoryFragment()
            Income -> IncomeFragment()
            Input -> InputFragment()
            More -> MoreFragment()
            Report -> ReportFragment()
            Category -> CategoryTypeFragment()
            Fix-> FixFragment()
        }
    }
}

fun FragmentActivity.navigateFragment(screen: AppScreen) {
    val fm = this.supportFragmentManager
    val transaction = fm.beginTransaction()
    val tagName = screen.javaClass.name
    fm.fragments.forEach {
        transaction.hide(it)
    }
    fm.findFragmentByTag(tagName)?.let { previous ->
        transaction.show(previous)
        transaction.commit()
        return
    } ?: run {
        screen.getScreenInstance().let { newFragment ->
            transaction.add(R.id.frameHome, newFragment, tagName)
            transaction.addToBackStack(tagName)
        }
        transaction.commit()
    }
}