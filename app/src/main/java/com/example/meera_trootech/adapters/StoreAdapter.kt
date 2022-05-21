package com.example.meera_trootech.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grubbrrarchana.utils.AppConstants
import com.example.meera_trootech.databinding.RawStoreBinding
import com.example.meera_trootech.model.Franquicia
import com.example.meera_trootech.presentation.menu.MenuActivity

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : StoreAdapter
 * Description : Use of this class for binding the Store list in adapter
 */
class StoreAdapter(
    private val activity: Activity, private val storeList: List<Franquicia>
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(val rawBinding: RawStoreBinding) : RecyclerView.ViewHolder(rawBinding.root) {
        fun setData(store: Franquicia)
        {
            rawBinding.apply {
                franquicia = store
            }
        }
    }

    // Bind view using Databinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = RawStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(view)
    }

    // do action on view using databinding
    override fun onBindViewHolder(holder: StoreViewHolder,position: Int) {
        if (holder != null && holder is StoreViewHolder) {
            var index = holder.adapterPosition
            storeList[index].let { holder.setData(it) }
            holder.rawBinding.lblStoreName.setOnClickListener(object : View.OnClickListener{
                override fun onClick(view: View?) {
                    var intent = Intent(activity,MenuActivity::class.java)
                    intent.putExtra(AppConstants.APIKEY, storeList[index].APIKEY)
                    intent.putExtra(AppConstants.STORENAME, storeList[index].negocio)
                    activity.startActivity(intent)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}