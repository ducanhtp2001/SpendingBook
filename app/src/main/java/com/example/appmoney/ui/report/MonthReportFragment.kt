package com.example.appmoney.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appmoney.databinding.FragmentMonthReportBinding
import com.example.appmoney.helper.TimeFormat
import com.example.appmoney.helper.TimeHelper
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar


class MonthReportFragment : Fragment() {

    private var _binding: FragmentMonthReportBinding? = null
    private val binding get() = _binding!!
    private var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonthReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatePicker()

    }
    private fun setupDatePicker() {
        updateDateText()
        binding.apply {
            btnPre.setOnClickListener { changeDate(-1) }
            btnAfter.setOnClickListener { changeDate(1) }
            tvMonthDate.setOnClickListener { showDatePicker() }
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
        binding.tvMonthDate.text = TimeHelper.getByFormat(calendar, TimeFormat.MonthDate)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }



}