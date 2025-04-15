package com.example.appmoney.ui.main.feature.categorytype.catExp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmoney.data.model.Category
import com.example.appmoney.databinding.FragmentCatExpBinding
import com.example.appmoney.ui.common.helper.BundleHelper
import com.example.appmoney.ui.common.helper.Constant.BUNDLE_KEY_CATEGORY
import com.example.appmoney.ui.main.feature.categorytype.viewPagger.CatTypeOnClickListener
import com.example.appmoney.ui.main.feature.categorytype.viewPagger.CategoryTypeAdapter
import com.example.appmoney.ui.common.helper.showApiResultToast
import com.example.appmoney.ui.main.feature.categorytype.CatTypeViewModel
import com.example.appmoney.ui.main.main_screen.AppScreen
import com.example.appmoney.ui.main.main_screen.ScreenHomeViewModel
import com.example.appmoney.ui.main.main_screen.navigateFragment


class CatExpFragment : Fragment(), CatTypeOnClickListener {

    private var _binding : FragmentCatExpBinding? = null
    private val bingding get() = _binding!!
    private  var adapter = CategoryTypeAdapter()

    private lateinit var viewModel: CatTypeViewModel
    private lateinit var sharedViewModel: ScreenHomeViewModel

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
        viewModel = ViewModelProvider(viewModelOnwer)[CatTypeViewModel::class.java]
        sharedViewModel = ViewModelProvider(viewModelOnwer)[ScreenHomeViewModel::class.java]
        adapter.setListener(this)
        bingding.rcCatExp.layoutManager = LinearLayoutManager(requireContext())
        bingding.rcCatExp.adapter = adapter
        sharedViewModel.expList.observe(viewLifecycleOwner){ list->
            adapter.submitList(list)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getExpenditureCat {
            showApiResultToast(false, it)
        }
    }

    override fun onClick(item: Category) {
        val bundle = BundleHelper.addParam(BUNDLE_KEY_CATEGORY, item).build()
        requireActivity().navigateFragment(AppScreen.Fix, bundle)
    }
}