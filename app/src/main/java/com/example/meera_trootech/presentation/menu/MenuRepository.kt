package com.example.meera_trootech.presentation.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meera_trootech.model.Menu
import com.example.meera_trootech.network.ApiInterface
import com.example.meera_trootech.network.Resource
import com.example.meera_trootech.network.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : MenuRepository
 * Description : Use of this class for get data from cloud
 */
class MenuRepository {
    private lateinit var mMenu : MutableLiveData<Resource<Menu>>
    fun doCallMenuDetails(apiKey: String): LiveData<Resource<Menu>> {
        mMenu = MutableLiveData()

        // Call API using retrofit
        ApiInterface.create().getMenuDetail(apiKey)?.enqueue(object :
            Callback<Menu?> {
            override fun onResponse(call: Call<Menu?>, response: Response<Menu?>) {
                try {
                    if (response != null) {
                        Log.d("tag", "Response Code:" + response.code())
                        val responseCode = response.code()
                        when (responseCode) {
                            200 -> {
                                if (response.isSuccessful) {
                                    mMenu.value = Resource<Menu>(
                                        Status.SUCCESS, response.body(), "")
                                }
                                else
                                {
                                    mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Error occurred while getting menu details.")
                                }
                            }
                            401 -> {
                                mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Failed to get menu details. : Handle unauthorized")

                            }
                            400 -> {
                                mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Failed to get menu details.: Bad Request")
                            }
                            500 -> {
                                mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Failed to get menu details. : Internal Server Error")
                            }
                            else -> {
                                mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Failed to get menu details. : Error ouccured")

                            }
                        }
                    } else {
                        mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Failed to get menu details. : Response = null")

                    }
                } catch (e: Exception) {
                    mMenu.value = Resource<Menu>(Status.ERROR, response.body(), "Failed to get menu details. : ${e.message}")

                }
            }

            override fun onFailure(call: Call<Menu?>, t: Throwable) {
                mMenu.value = Resource<Menu>(Status.ERROR, null, "Failed :${t.message}")
            }

        })
        return mMenu
    }

}