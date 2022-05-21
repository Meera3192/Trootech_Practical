package com.example.meera_trootech.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grubbrrarchana.utils.AppConstants
import com.example.meera_trootech.databinding.RawMenuBinding
import com.example.meera_trootech.model.Category
import com.example.meera_trootech.presentation.item.ItemActivity
import com.google.gson.Gson

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : MenuAdapter
 * Description : Use of this class for binding the Category list in adapter
 */
class MenuAdapter(private val activity: Activity, private var categoryList: ArrayList<Category>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    class MenuViewHolder(val rawBinding: RawMenuBinding) : RecyclerView.ViewHolder(rawBinding.root) {
        fun setData(data: Category)
        {
            rawBinding.apply {
                category =data
            }
        }
    }

    // Bind view using Databinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = RawMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(view)
    }

    // do action on view using databinding
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        if (holder != null && holder is MenuViewHolder) {
            var index = holder.adapterPosition
            categoryList[index].let { holder.setData(it) }
            holder.rawBinding.lblCategoryName.setOnClickListener(object : View.OnClickListener{
                override fun onClick(view: View?) {
                    var intent = Intent(activity,ItemActivity::class.java)
                    intent.putExtra(AppConstants.CATEGORYDETAILS, Gson().toJson(categoryList[index]))
                    activity.startActivity(intent)
                }
            })
        }

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}