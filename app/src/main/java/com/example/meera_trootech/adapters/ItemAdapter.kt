package com.example.meera_trootech.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meera_trootech.databinding.RawItemBinding
import com.example.meera_trootech.model.Item
import com.example.meera_trootech.presentation.item.dialog.ItemDetailsDialog

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : ItemAdapter
 * Description : Use of this class for binding the Item list in adapter
 */
class ItemAdapter(
    private val activity: Activity, private val itemList: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val rawBinding: RawItemBinding) : RecyclerView.ViewHolder(rawBinding.root) {

        fun setData(data: Item)
        {
            rawBinding.apply {
                item = data
            }
        }
    }

    // Bind view using Databinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = RawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    // do action on view using databinding
    override fun onBindViewHolder(holder: ItemViewHolder,position: Int) {
        if (holder != null && holder is ItemViewHolder) {
            var index = holder.adapterPosition
            itemList[index].let { holder.setData(it) }
            holder.rawBinding.imgPlus.setOnClickListener(object : View.OnClickListener{
                override fun onClick(view: View?) {
                    showItemDetailsDialog(itemList[index])
                }
            })
        }
    }

    // open item details dialog
    private fun showItemDetailsDialog(item: Item) {
        val itemDetailsDialog = ItemDetailsDialog(activity,item)
        itemDetailsDialog.show()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}