package com.example.meera_trootech.presentation.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meera_trootech.R
import com.example.meera_trootech.adapters.StoreAdapter
import com.example.meera_trootech.databinding.ActivityStoreBinding
import com.example.meera_trootech.model.Franquicias
import com.example.meera_trootech.network.Resource
import com.example.meera_trootech.network.Status
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : StoreActivity
 * Description : Use of this class for display the store list
 */
class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var adapter: StoreAdapter

    private val TAG = "StoreActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bind view
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize viewmodel
        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        initView();
    }

    //initialize view
    private fun initView() {
        setTitle(R.string.lbl_store)
        val layoutManager = LinearLayoutManager(this)
        binding.rvStore.layoutManager = layoutManager
        binding.rvStore.addItemDecoration(
            DividerItemDecoration(
                this@StoreActivity,
                layoutManager.orientation
            )
        )

        callFranquiciaDetailsApi()
    }

    // Call API for getting store details
    private fun callFranquiciaDetailsApi() {
        binding.loading.visibility = View.VISIBLE
        storeViewModel.getStoresDetails().observe(this@StoreActivity, object :
            Observer<Resource<Franquicias>> {
            override fun onChanged(result: Resource<Franquicias>?) {
                Log.d(TAG + " : ", Gson().toJson(result))
                if (result != null && result.status != null && result.data != null && result.status.equals(
                        Status.SUCCESS
                    )
                ) {
                    binding.loading.visibility = View.GONE
                    lifecycleScope.launch(Dispatchers.IO)
                    {
                        var storeList = result.data
                        setadapter(storeList)
                    }
                }else if(result?.status != null && result.status.equals(Status.ERROR))
                {
                    Toast.makeText(this@StoreActivity,result.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    // After getting store data bind the adapter
    private suspend fun setadapter(storeList: Franquicias) {
        withContext(Dispatchers.Main) {
            if (storeList.franquicias != null && storeList.franquicias.size > 0) {
                adapter =
                    StoreAdapter(this@StoreActivity, storeList.franquicias)
                binding.rvStore.adapter = adapter

            } else {
                Toast.makeText(this@StoreActivity,"Store details not found",Toast.LENGTH_LONG).show()
            }
        }
    }
}
