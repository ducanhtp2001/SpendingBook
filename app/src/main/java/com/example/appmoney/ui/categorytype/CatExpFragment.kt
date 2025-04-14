package com.example.appmoney.ui.categorytype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmoney.data.model.Category
import com.example.appmoney.databinding.FragmentCatExpBinding
import com.example.appmoney.helper.showApiResultToast
import com.example.appmoney.ui.edit.EditCategoryViewModel
import com.example.appmoney.ui.mainScreen.AppScreen
import com.example.appmoney.ui.mainScreen.navigateFragment


class CatExpFragment : Fragment(), CatTypeOnClickListener {

    private var _binding : FragmentCatExpBinding? = null
    private val bingding get() = _binding!!
    private  var adapter = CategoryTypeAdapter()
    private lateinit var viewModel: CatTypeViewModel
    private lateinit var viewModelEdit: EditCategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatExpBinding.inflate(inflater,container, false)
        return bingding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelOnwer = this.parentFragment ?: requireActivity()
        viewModelEdit = ViewModelProvider(this)[EditCategoryViewModel::class.java]
        viewModel = ViewModelProvider(viewModelOnwer)[CatTypeViewModel::class.java]
        adapter.setListener(this)
        bingding.rcCatExp.layoutManager = LinearLayoutManager(requireContext())
        bingding.rcCatExp.adapter = adapter
        viewModel.expList.observe(viewLifecycleOwner){ list->
            adapter.submitList(list)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExpenditureCat {
            showApiResultToast(false, it)
        }
    }

    override fun onClick(item: Category) {
        viewModelEdit.setCat(item)
        requireActivity().navigateFragment(AppScreen.Fix)
    }
}