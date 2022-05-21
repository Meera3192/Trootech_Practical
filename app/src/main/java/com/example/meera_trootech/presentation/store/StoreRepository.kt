package com.example.meera_trootech.presentation.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meera_trootech.model.Franquicias
import com.example.meera_trootech.network.ApiInterface
import com.example.meera_trootech.network.Resource
import com.example.meera_trootech.network.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : StoreRepository
 * Description : Use of this class for get data from cloud
 */
class StoreRepository {

    private lateinit var mStoreDetails : MutableLiveData<Resource<Franquicias>>
    fun doCallStoresDetails(): LiveData<Resource<Franquicias>> {
        mStoreDetails =MutableLiveData()

        // Call API using retrofit
        ApiInterface.create().getStoresDetail()?.enqueue(object :
            Callback<Franquicias?> {
            override fun onResponse(call: Call<Franquicias?>, response: Response<Franquicias?>) {
                try {
                    if (response != null) {
                        Log.d("tag", "Response Code:" + response.code())
                        val responseCode = response.code()
                        when (responseCode) {
                            200 -> {
                                if (response.isSuccessful) {
                                    mStoreDetails.value = Resource<Franquicias>(
                                        Status.SUCCESS, response.body(), "")
                                }
                                else
                                {
                                    mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Error occurred while getting Stores details.")
                                }
                            }
                            401 -> {
                                mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Failed to get Stores details. : Handle unauthorized")

                            }
                            400 -> {
                                mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Failed to get Stores details.: Bad Request")
                            }
                            500 -> {
                                mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Failed to get Stores details. : Internal Server Error")
                            }
                            else -> {
                                mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Failed to get Stores details. : Error ouccured")

                            }
                        }
                    } else {
                        mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Failed to get Stores details. : Response = null")

                    }
                } catch (e: Exception) {
                    mStoreDetails.value = Resource<Franquicias>(Status.ERROR, response.body(), "Failed to get Stores details. : ${e.message}")

                }
            }

            override fun onFailure(call: Call<Franquicias?>, t: Throwable) {
                mStoreDetails.value = Resource<Franquicias>(Status.ERROR, null, "Failed :${t.message}")
            }

        })
        return mStoreDetails
    }
}