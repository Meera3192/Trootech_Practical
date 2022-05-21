package com.example.meera_trootech.presentation.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grubbrrarchana.utils.AppConstants
import com.example.meera_trootech.adapters.MenuAdapter
import com.example.meera_trootech.databinding.ActivityMenuBinding
import com.example.meera_trootech.model.*
import com.example.meera_trootech.network.Resource
import com.example.meera_trootech.network.Status
import com.google.gson.Gson
import kotlinx.coroutines.*

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : MenuActivity
 * Description : Use of this class for display the Category list
 */
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var adapter: MenuAdapter
    private val TAG = "MenuActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bind the view
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize view model
        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        initView();
    }

    // initialize view
    private fun initView() {

        if(intent.hasExtra(AppConstants.STORENAME))
        {
            setTitle(intent.getStringExtra(AppConstants.STORENAME).toString())
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvMenu.layoutManager = layoutManager
        binding.rvMenu.addItemDecoration(
            DividerItemDecoration(
                this@MenuActivity,
                layoutManager.orientation
            )
        )
        var apiKey : String =""

        if(intent.hasExtra(AppConstants.APIKEY))
        {
            apiKey = intent.getStringExtra(AppConstants.APIKEY).toString()
            Log.d("APIKey : ",apiKey)
        }

        callMenuDetailsApi(apiKey)
    }

    // Call API to get Menu details
    private fun callMenuDetailsApi(apiKey: String) {
        binding.loading.visibility = View.VISIBLE
        menuViewModel.getMenuDetails(apiKey).observe(this@MenuActivity, object :
            Observer<Resource<Menu>> {
            override fun onChanged(result: Resource<Menu>?) {
                Log.d(TAG + " : ", Gson().toJson(result));
                if (result != null && result.status != null && result.data != null && result.status.equals(
                        Status.SUCCESS
                    )
                ) {
                    binding.loading.visibility = View.GONE
                    lifecycleScope.launch(Dispatchers.IO)
                    {
                        var menuDetails = result.data
                        setCategoryItemList(menuDetails)

                    }
                } else if(result?.status != null && result.status.equals(Status.ERROR))
                {
                    Toast.makeText(this@MenuActivity,result.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    // Create category wise list
    private suspend fun setCategoryItemList(menuDetails: Menu) {
        // Groupby category name
        var categoryItem = menuDetails.data.groupBy({ it.categoria.nombremenu })
        var categoryList = ArrayList<Category>()

        // Map<String,List<Data>> to ArrayList<Item> for use of selected fields
        categoryItem.mapValues {
            //Log.d("item", Gson().toJson(it.value))
            var itemList = ArrayList<Item>()
            for (data in it.value) {
                var item = Item(
                    description = data.descripcion ?: "",
                    itemid = data.idmenu ?: "",
                    itemname = data.nombre ?: "",
                    price = data.precioSugerido ?: "",
                    quantity = 1
                )
                itemList.add(item)

            }
            var category = Category(it.key, itemList)
            categoryList.add(category)

        }

        setadapter(categoryList)
    }

    // set Category list adapter
    private suspend fun setadapter(categoryList: ArrayList<Category>) {
        withContext(Dispatchers.Main) {
            if (categoryList != null && categoryList.size > 0) {
                adapter =
                    MenuAdapter(this@MenuActivity, categoryList)
                binding.rvMenu.adapter = adapter

            } else {
                Toast.makeText(this@MenuActivity,"Menu details not found", Toast.LENGTH_LONG).show()
            }
        }
    }
}
