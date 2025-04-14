package com.example.appmoney.ui.mainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appmoney.R
import com.example.appmoney.databinding.ActivityScreenHomeBinding
import com.example.appmoney.ui.input.InputFragment

class ScreenHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateFragment(AppScreen.Input)
        binding.navHome.setOnItemSelectedListener { item ->
            handleFragment(item.itemId)
            true
        }


    }

    override fun onBackPressed() {
        val canBack = this.supportFragmentManager.fragments.size > 2
        when {
            canBack -> super.onBackPressed()
            else -> {
                // cancel app
            }
        }


    }

    private fun handleFragment(itemId: Int) {
        val screen = when(itemId){
            R.id.item_input -> AppScreen.Input
            R.id.item_history_trans -> AppScreen.HistoryTrans
            R.id.item_report -> AppScreen.Report
            R.id.item_more -> AppScreen.More
            else -> return
        }
        navigateFragment(screen)
    }
}