package com.example.appmoney.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmoney.R
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.CategoryColor
import com.example.appmoney.data.model.CategoryColorWrapper
import com.example.appmoney.data.model.CategoryImage
import com.example.appmoney.data.model.CategoryImageWrapper
import com.example.appmoney.data.repository.Repository
import com.example.appmoney.databinding.FragmentEditCategoryBinding
import com.example.appmoney.helper.TabObject
import com.example.appmoney.helper.showApiResultToast
import com.example.appmoney.ui.common.adapter.CategoryColorAdapter
import com.example.appmoney.ui.common.adapter.CategoryColorListener
import com.example.appmoney.ui.common.adapter.CategoryImageAdapter
import com.example.appmoney.ui.common.adapter.CategoryImageListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class EditCategoryFragment : Fragment(), CategoryImageListener,
    CategoryColorListener {

    private var _binding: FragmentEditCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditCategoryViewModel
    private val imageAdapter = CategoryImageAdapter()
    private val colorAdapter = CategoryColorAdapter()
    private lateinit var type: String
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        val repository = Repository()

        viewModel = ViewModelProvider(requireActivity())[EditCategoryViewModel::class.java]
        type = if (TabObject.tabPosition == 0) {
            getString(R.string.cat_expenditure)
        } else {
            getString(R.string.cat_income)
        }

        initViews()
        observer()
        binding.apply {
            rcImages.layoutManager = GridLayoutManager(requireContext(), 4)
            rcColors.layoutManager = GridLayoutManager(requireContext(), 4)
            rcImages.adapter = imageAdapter
            rcColors.adapter = colorAdapter

            createCategory()

            backButton.setOnClickListener {
                backToPrevious()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createCategory() {
        binding.saveButton.setOnClickListener {
            var sImage = viewModel.getImageSelected()
            var sColor = viewModel.getColorSelected()
            var sName = binding.edtTransaction.text.toString()

            if (sImage == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn biểu tượng", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (sColor == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn màu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (sName.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val cat = Category(
                image = sImage,
                color = sColor,
                desCat = sName,
                timeCreate = System.currentTimeMillis()
            )
            viewModel.addCategory(
                type, cat, onSuccess = {
                    showApiResultToast(isSuccess = true)
                    backToPrevious()
                },
                onFailure = { error ->
                    showApiResultToast(isSuccess = false, message = error)
                })
            /*val catgoryMap =CategoryMap.toMap(cat)
            var user = auth.currentUser
            user?.let {
                val idItem = type +System.currentTimeMillis()
                db.collection("User").document(user.uid)
                    .collection("Category").document(type)
                    .collection("Item").document(idItem)
                    .set(catgoryMap)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(),"Thành công",Toast.LENGTH_SHORT).show()
                        catTypeViewModel.loadIncome()
                        catTypeViewModel.loadExpenditure()
                        backToPrevious()

                    }.addOnFailureListener { err ->
                        Toast.makeText(requireContext(),"Thất bại: ${err.message}",Toast.LENGTH_SHORT).show()
                    }
            }*/
        }
    }


    private fun backToPrevious() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun initViews() {

        imageAdapter.setListener(this)
        colorAdapter.setListener(this)

        val listImage = listOf(
            CategoryImage.BOOK,
            CategoryImage.BUS,
            CategoryImage.GAME,
            CategoryImage.BASKET,
            CategoryImage.BALL,
            CategoryImage.EDIT
        )
        val listColor = listOf(
            CategoryColor.BLUE,
            CategoryColor.GREEN,
            CategoryColor.ORANGE,
            CategoryColor.PURPLE,
            CategoryColor.RED
        )
        viewModel.loadItemsImage(listImage)
        viewModel.loadItemsColor(listColor)
    }

    private fun observer() {
        viewModel.images.observe(viewLifecycleOwner) { images ->
            imageAdapter.submitList(images)
        }

        viewModel.colors.observe(viewLifecycleOwner) { colors ->
            colors.firstOrNull { it.isSelected }?.let {
                imageAdapter.setTintColor(it.categoryColor)
            }
            colorAdapter.submitList(colors)
        }
    }


    override fun onItemClick(categoryImage: CategoryImageWrapper) {
        viewModel.onSelectedCategoryImage(categoryImage.categoryImage)

    }

    override fun onItemClick(categoryColor: CategoryColorWrapper) {
        viewModel.onSelectedCategoryColor(categoryColor.categoryColor)

    }


}

