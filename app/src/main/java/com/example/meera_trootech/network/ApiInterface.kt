package com.example.meera_trootech.network

import com.example.grubbrrarchana.utils.AppConstants
import com.example.meera_trootech.model.Franquicias
import com.example.meera_trootech.model.Menu
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Meera Bhadania on 21/5/22.
 * Interface Name : ApiInterface
 * Description : Use of this class for calling API
 */
interface ApiInterface {

    // Store API
    @Headers(AppConstants.HEADER)
    @GET(AppConstants.CATEGORY)
    fun getStoresDetail(): Call<Franquicias>?

    // Menu API
    @GET(AppConstants.ITEMS)
    fun getMenuDetail(@Header("APIKEY") apiKey: String): Call<Menu>?

    // Create retrofit object
    companion object {
        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstants.BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}