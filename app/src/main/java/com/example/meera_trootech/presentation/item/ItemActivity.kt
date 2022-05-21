package com.example.meera_trootech.presentation.item

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grubbrrarchana.utils.AppConstants
import com.example.meera_trootech.adapters.ItemAdapter
import com.example.meera_trootech.databinding.ActivityItemBinding
import com.example.meera_trootech.model.Category
import com.example.meera_trootech.model.Item
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : ItemActivity
 * Description : Use of this class for display the Item list
 */
class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var adapter: ItemAdapter

    private val TAG = "itemActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bind view
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView();
    }

    // Initialize view
    private fun initView() {

        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager
        binding.rvItem.addItemDecoration(
            DividerItemDecoration(
                this@ItemActivity,
                layoutManager.orientation
            )
        )

        var category = Category("", ArrayList<Item>())
        if (intent.hasExtra(AppConstants.CATEGORYDETAILS)) {
            var json = intent.getStringExtra(AppConstants.CATEGORYDETAILS).toString()
            category = Gson().fromJson(json, Category::class.java)
            setTitle(category.categoryname)
        }

        setItemDetails(item = category.item)
    }

    // set Item detail list
    private fun setItemDetails(item: List<Item>) {
        binding.loading.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO)
        {
            setadapter(item)
        }

    }

    // set item list to adapter
    private suspend fun setadapter(itemList: List<Item>) {
        withContext(Dispatchers.Main) {
            if (itemList != null && itemList.size > 0) {
                adapter =
                    ItemAdapter(this@ItemActivity, itemList)
                binding.rvItem.adapter = adapter
                binding.loading.visibility = View.GONE
            } else {
                Toast.makeText(this@ItemActivity,"Item details not found", Toast.LENGTH_LONG).show()
            }
        }
    }
}