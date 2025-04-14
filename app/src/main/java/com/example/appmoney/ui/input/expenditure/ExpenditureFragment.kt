package com.example.appmoney.ui.input.expenditure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmoney.R
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.CategoryColor
import com.example.appmoney.data.model.CategoryImage
import com.example.appmoney.data.model.CategoryType
import com.example.appmoney.databinding.FragmentExpenditureBinding
import com.example.appmoney.ui.common.adapter.CategoryAdapter
import com.example.appmoney.ui.common.adapter.CategoryListener
import com.example.appmoney.ui.input.income.InComeViewModel
import com.example.appmoney.ui.mainScreen.AppScreen
import com.example.appmoney.ui.mainScreen.navigateFragment


class ExpenditureFragment : Fragment(),CategoryListener {
    private var _binding : FragmentExpenditureBinding? = null
    private val binding get() = _binding!!
    private val adapter = CategoryAdapter()

    private lateinit var viewModel: InComeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenditureBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setListener(this)

        binding.apply {
            rcExpenditure.layoutManager = GridLayoutManager(requireContext(),4)
            rcExpenditure.adapter = adapter
        }
        initViewModel()
        observer()
    }
    private fun observer() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[InComeViewModel::class.java]
        val list = mutableListOf(
            Category(image = CategoryImage.BASKET,color = CategoryColor.YELLOW, desCat = "Mua sắm"),
            Category(image = CategoryImage.BALL,color = CategoryColor.PINK, desCat = "Thể Thao"),
            Category(image = CategoryImage.BOOK, color = CategoryColor.BLUE, desCat = "Giáo dục"),
            Category(image = CategoryImage.BUS,color = CategoryColor.RED, desCat = "Đi lại"),
            Category(image = CategoryImage.GAME,color = CategoryColor.GREEN, desCat = "Giải trí"),
            Category(image = CategoryImage.EDIT, desCat = "Chỉnh sửa", categoryType = CategoryType.BUTTON)
        )
        viewModel.init(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(category: Category) {
        if (category.categoryType == CategoryType.BUTTON) {
            requireActivity().navigateFragment(AppScreen.Category)
        }
        else {
            viewModel.onSelectedCategory(category)
        }
    }


}