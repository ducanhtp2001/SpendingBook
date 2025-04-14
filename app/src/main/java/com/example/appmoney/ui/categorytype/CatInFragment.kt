package com.example.appmoney.ui.categorytype

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoney.data.model.Category
import com.example.appmoney.databinding.FragmentCatInBinding
import com.example.appmoney.helper.showApiResultToast
import com.example.appmoney.ui.edit.EditCategoryViewModel
import com.example.appmoney.ui.mainScreen.AppScreen
import com.example.appmoney.ui.mainScreen.navigateFragment

class CatInFragment : Fragment(), CatTypeOnClickListener {

    private var _binding: FragmentCatInBinding? = null
    private val binding get() = _binding!!
    private val adapter = CategoryTypeAdapter()
    private lateinit var viewModel: CatTypeViewModel
    private lateinit var viewModelEdit: EditCategoryViewModel
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
        viewModelEdit = ViewModelProvider(this)[EditCategoryViewModel::class.java]
        viewModel = ViewModelProvider(viewModelOnwer)[CatTypeViewModel::class.java]
        adapter.setListener(this)
        binding.rcCatIn.layoutManager = LinearLayoutManager(requireContext())
        binding.rcCatIn.adapter = adapter
        setupSwipeToDelete(binding.rcCatIn, adapter)
        viewModel.incomeList.observe(viewLifecycleOwner) { list ->
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
                    AlertDialog.Builder(requireContext())
                        .setTitle("Xóa danh mục")
                        .setMessage("Bạn có chắc muốn xóa danh mục?")
                        .setPositiveButton("Xóa") { _, _ ->
                            viewModel.delCategory(item.idCat, "Income",
                                onFailure = {showApiResultToast(false, it)})
                        }.setNeutralButton("Hủy") { _, _ ->
                            adapter.notifyItemChanged(position)
                        }
                        .setCancelable(false)
                        .show()
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rcCatIn)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onResume() {
        super.onResume()
        viewModel.getIncomeCat { showApiResultToast(false,it)}
    }

    override fun onClick(item: Category) {
        viewModelEdit.setCat(item)
        requireActivity().navigateFragment(AppScreen.Fix)
    }


}