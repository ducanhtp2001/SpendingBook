package com.example.appmoney.ui.transactionhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmoney.data.model.Category
import com.example.appmoney.data.model.CategoryColor
import com.example.appmoney.data.model.CategoryImage
import com.example.appmoney.data.model.Transaction
import com.example.appmoney.data.model.TransAndCat
import com.example.appmoney.databinding.FragmentHistoryTransBinding


class HistoryTransFragment : Fragment() {
    private var _binding: FragmentHistoryTransBinding? = null
    private val binding get() = _binding!!
    private val adapter = HistoryTransAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryTransBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rcHistoryTrans.layoutManager = LinearLayoutManager(requireContext())
            rcHistoryTrans.adapter = adapter
        }
        val list = mutableListOf(
            TransAndCat(
                Category(
                    image = CategoryImage.BOOK,
                    color = CategoryColor.BLUE,
                    desCat = "Giáo dục"
                ), Transaction(inCome = 100000, note = "Mua sách")
            ),
            TransAndCat(
                Category(image = CategoryImage.BUS, color = CategoryColor.RED, desCat = "Đi lại"),
                Transaction(inCome = 300000, note = "Đi học")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.GAME,
                    color = CategoryColor.GREEN,
                    desCat = "Giải trí"
                ),
                Transaction(inCome = 50000, note = "Nạp thẻ game")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.BASKET,
                    color = CategoryColor.YELLOW,
                    desCat = "Mua sắm"
                ),
                Transaction(inCome = 1000000, note = "Mua đồ ăn, đồ uống")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.BALL,
                    color = CategoryColor.PINK,
                    desCat = "Thể thao"
                ),
                Transaction(inCome = 150000, note = "Thuê sân")
            ),TransAndCat(
                Category(
                    image = CategoryImage.BOOK,
                    color = CategoryColor.BLUE,
                    desCat = "Giáo dục"
                ), Transaction(inCome = 100000, note = "Mua sách")
            ),
            TransAndCat(
                Category(image = CategoryImage.BUS, color = CategoryColor.RED, desCat = "Đi lại"),
                Transaction(inCome = 300000, note = "Đi học")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.GAME,
                    color = CategoryColor.GREEN,
                    desCat = "Giải trí"
                ),
                Transaction(inCome = 50000, note = "Nạp thẻ game")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.BASKET,
                    color = CategoryColor.YELLOW,
                    desCat = "Mua sắm"
                ),
                Transaction(inCome = 1000000, note = "Mua đồ ăn, đồ uống")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.BALL,
                    color = CategoryColor.PINK,
                    desCat = "Thể thao"
                ),
                Transaction(inCome = 150000, note = "Thuê sân")
            ),TransAndCat(
                Category(
                    image = CategoryImage.BOOK,
                    color = CategoryColor.BLUE,
                    desCat = "Giáo dục"
                ), Transaction(inCome = 100000, note = "Mua sách")
            ),
            TransAndCat(
                Category(image = CategoryImage.BUS, color = CategoryColor.RED, desCat = "Đi lại"),
                Transaction(inCome = 300000, note = "Đi học")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.GAME,
                    color = CategoryColor.GREEN,
                    desCat = "Giải trí"
                ),
                Transaction(inCome = 50000, note = "Nạp thẻ game")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.BASKET,
                    color = CategoryColor.YELLOW,
                    desCat = "Mua sắm"
                ),
                Transaction(inCome = 1000000, note = "Mua đồ ăn, đồ uống")
            ),
            TransAndCat(
                Category(
                    image = CategoryImage.BALL,
                    color = CategoryColor.PINK,
                    desCat = "Thể thao"
                ),
                Transaction(inCome = 150000, note = "Thuê sân")
            )


        )
        adapter.submitList(list)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}