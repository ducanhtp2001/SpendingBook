package com.example.appmoney.ui.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmoney.R
import com.example.appmoney.databinding.FragmentInputBinding
import com.example.appmoney.helper.TabObject
import com.example.appmoney.helper.TimeFormat
import com.example.appmoney.helper.TimeHelper
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Calendar


class InputFragment : Fragment() {
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    private var calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabSelected()
        setupDatePicker()

    }

    private fun setupDatePicker() {
        updateDateText()
        binding.apply {
            btnPre.setOnClickListener { changeDate(-1) }
            btnAfter.setOnClickListener { changeDate(1) }
            tvDate.setOnClickListener { showDatePicker() }
        }
    }

    private fun showDatePicker() {
        var datePicker = MaterialDatePicker.Builder.datePicker()
            .build()
        datePicker.addOnPositiveButtonClickListener { selection ->
            calendar.timeInMillis = selection
            updateDateText()
        }
        datePicker.show(childFragmentManager,"DATE_PICKER")
    }

    private fun changeDate(day: Int) {
        calendar.add(Calendar.DAY_OF_MONTH,day)
        updateDateText()
    }

    private fun updateDateText() {
//        val dateFormat = SimpleDateFormat("EEE, dd/MM/yyyy", Locale.getDefault())
//        binding.tvDate.text = dateFormat.format(calendar.time)

        binding.tvDate.text = TimeHelper.getByFormat(calendar, TimeFormat.Date)
    }

    private fun setupTabSelected() {
        binding.tabMoney.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    TabObject.tabPosition = it.position
                    if (it.position == 0) {
                        binding.tvTypeMoney.text = getString(R.string.expenditure_money)
                        binding.btnSave.text = getString(R.string.save_expenditure)

                    } else {
                        binding.tvTypeMoney.text = getString(R.string.income_money)
                        binding.btnSave.text = getString(R.string.save_income)

                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    private fun setupViewPager() {
        val adapter = ViewpagerAdapter(childFragmentManager, lifecycle)
        binding.Vp.adapter = adapter
        TabLayoutMediator(binding.tabMoney, binding.Vp) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.expenditure_money)
                }

                1 -> {
                    tab.text = getString(R.string.income_money)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.Vp.setCurrentItem(TabObject.tabPosition, true)
    }
}