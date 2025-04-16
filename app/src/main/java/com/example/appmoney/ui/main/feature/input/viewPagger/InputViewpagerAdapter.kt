package com.example.appmoney.ui.main.feature.input.viewPagger

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appmoney.ui.main.feature.input.expenditure.ExpenditureFragment
import com.example.appmoney.ui.main.feature.input.income.IncomeFragment

class InputViewpagerAdapter(fragmentManager:FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when( position){
            0 ->{
                ExpenditureFragment()
            }
            else ->{
                IncomeFragment()
            }
        }
    }
}