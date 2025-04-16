package com.example.appmoney.ui.main.feature.categorytype.catIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoney.data.model.Category
import com.example.appmoney.databinding.FragmentCatInBinding
import com.example.appmoney.ui.common.helper.BundleHelper
import com.example.appmoney.ui.common.helper.Constant.BUNDLE_KEY_CATEGORY
import com.example.appmoney.ui.common.helper.DialogHelper
import com.example.appmoney.ui.common.helper.showApiResultToast
import com.example.appmoney.ui.main.feature.categorytype.CatTypeViewModel
import com.example.appmoney.ui.main.feature.categorytype.viewPagger.CatTypeOnClickListener
import com.example.appmoney.ui.main.feature.categorytype.viewPagger.CategoryTypeAdapter
import com.example.appmoney.ui.main.main_screen.AppScreen
import com.example.appmoney.ui.main.main_screen.ScreenHomeViewModel
import com.example.appmoney.ui.main.main_screen.navigateFragment

class CatInFragment : Fragment(), CatTypeOnClickListener {

    private var _binding: FragmentCatInBinding? = null
    private val binding get() = _binding!!
    private val adapter = CategoryTypeAdapter()

    private lateinit var viewModel: CatTypeViewModel
    private lateinit var sharedViewModel: ScreenHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelOnwer = this.parentFragment ?: requireActivity()
        viewModel = ViewModelProvider(viewModelOnwer)[CatTypeViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[ScreenHomeViewModel::class.java]
        adapter.setListener(this)
        binding.rcCatIn.layoutManager = LinearLayoutManager(requireContext())
        binding.rcCatIn.adapter = adapter
        setupSwipeToDelete(binding.rcCatIn, adapter)
        sharedViewModel.incomeList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView, adapter: CategoryTypeAdapter) {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val item = adapter.currentList[position]

                    DialogHelper.showRequestDialog(
                        requireContext(),
                        "Xóa danh mục",
                        "Bạn có chắc muốn xóa danh mục?",
                        onConfirm = {
                            viewModel.delCategory(
                                "Income", item.idCat,
                                onSuccess = {
                                    showApiResultToast(true)
                                    val newList = adapter.currentList.toMutableList()
                                    newList.removeAt(position)
                                    adapter.submitList(newList)
                                },
                                onFailure = { showApiResultToast(false, it) })
                        },
                        onCancel = {
                            adapter.notifyItemChanged(position)
                        }
                    )
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getIncomeCat { showApiResultToast(false, it) }
    }

    override fun onClick(item: Category) {
        val bundle = BundleHelper.addParam(BUNDLE_KEY_CATEGORY, item).build()
        requireActivity().navigateFragment(AppScreen.Fix, bundle)
    }
}