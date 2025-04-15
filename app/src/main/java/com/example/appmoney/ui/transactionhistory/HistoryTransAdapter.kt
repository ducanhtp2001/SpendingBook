package com.example.appmoney.ui.transactionhistory

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appmoney.data.model.TransAndCat
import com.example.appmoney.databinding.ItemHistoryTransBinding


class TransandCatDiffCallback : DiffUtil.ItemCallback<TransAndCat>() {
    override fun areItemsTheSame(oldItem: TransAndCat, newItem: TransAndCat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TransAndCat, newItem: TransAndCat): Boolean {
        return oldItem == newItem
    }

}

interface TransandCatListener {
    fun onItemClick(TransandCat: TransAndCat)
}

class HistoryTransAdapter :
    ListAdapter<TransAndCat, HistoryTransAdapter.TransandCatViewHolder>(
        TransandCatDiffCallback()
    ) {

        private var listener: TransandCatListener? = null
        fun setListener(listener: TransandCatListener) {
            this.listener = listener
        }

    inner class TransandCatViewHolder(val binding: ItemHistoryTransBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(
            item: TransAndCat,
            isSelected: Boolean
        ) {
            binding.apply {
                imgHis.setImageResource(item.category.image.resource)
                imgHis.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(root.context, item.category.color.resource))
                tvHis.text = item.category.desCat
                noteHis.text = "(" + item.trans.note + ")"
                moneyHis.text = item.trans.amount.toString()+ "đ"
                root.setOnClickListener {
                    listener?.onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransandCatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransandCatViewHolder(
            ItemHistoryTransBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransandCatViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, item.category.isSelected)
    }
}