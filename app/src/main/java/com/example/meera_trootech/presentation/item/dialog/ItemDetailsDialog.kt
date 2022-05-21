package com.example.meera_trootech.presentation.item.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.grubbrrarchana.utils.AppConstants
import com.example.meera_trootech.databinding.DialogItemdetailsBinding
import com.example.meera_trootech.model.Item
import com.example.meera_trootech.utils.MyApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : ItemDetailsDialog
 * Description : Use of this class for display item details in dialog
 */
class ItemDetailsDialog(activity: Activity, item: Item) : Dialog(activity) {
    private lateinit var binding: DialogItemdetailsBinding
    private var activity: Activity
    private var mItem: Item
    private val gson: Gson
    private var itemList: ArrayList<Item>

    // initialize objects
    init {
        this.activity = activity
        this.mItem = item
        gson = Gson()
        itemList = ArrayList<Item>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // bind view
        binding = DialogItemdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // window layout
        window?.setLayout(
            (activity?.resources?.getDimension(com.intuit.sdp.R.dimen._250sdp))?.toInt()!!,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initView()
    }

    // initialize view
    private fun initView() {

        checkCartItemDetails()

        // bind item details
        binding.lblItemName.text = mItem.itemname
        var itemPrice = (mItem.quantity * mItem.price.toDouble())
        binding.lblItemprice.text =
            "$" + String.format("%.2f", itemPrice)
        binding.etQuantity.setText(mItem.quantity.toString())

        // On Plus button click increse quantity and update price
        binding.imgPlus.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                updatePrice(++mItem.quantity)
            }
        })

        // On minus button click decrease the quantity and update the price
        binding.imgMins.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (mItem.quantity != 1) {
                    updatePrice(--mItem.quantity)
                } else {
                    binding.etQuantity.setText(mItem.quantity.toString())
                }
            }
        })

        // close the dialog on click of cancel
        binding.btnCancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                dismiss()
            }
        })

        // Save/update the Item on click of confirm
        binding.btnConfirm.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                addItemToCart(mItem)
                dismiss()
            }
        })
    }

    // If item is already added in cart then update the quantity
    private fun checkCartItemDetails() {

        itemList = gson.fromJson<ArrayList<Item>>(
            MyApplication.prefs?.getString(AppConstants.CARTITEM),
            object : TypeToken<ArrayList<Item>?>() {}.type
        ) ?: ArrayList<Item>()
        if (itemList != null && itemList.size > 0) {
            for (item in itemList) {
                if (item.itemid.equals(mItem.itemid)) {
                    mItem.quantity = item.quantity
                }
            }
        }
    }

    // Add/Update item to the cart list
    private fun addItemToCart(mItem: Item) {

        var cout = 0
        itemList.forEachIndexed { index, item ->
            if (item.itemid.equals(mItem.itemid)) {
                cout++
                itemList[index] = mItem
            }
        }

        if (cout == 0) {
            itemList.add(mItem)
        }
        MyApplication.prefs?.setString(AppConstants.CARTITEM, gson.toJson(itemList))

    }

    // Update price on click of minu/plus button
    private fun updatePrice(quantity: Int) {
        if (mItem != null && mItem.price != null) {
            var updatedprice = quantity * mItem.price.toDouble()
            binding.lblItemprice.text = "$" + String.format("%.2f", updatedprice)
            binding.etQuantity.setText(quantity.toString())
        }
    }


}