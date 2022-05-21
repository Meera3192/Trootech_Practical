package com.example.meera_trootech.presentation.store

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.meera_trootech.model.Franquicias
import com.example.meera_trootech.network.Resource

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : StoreViewModel
 * Description : This class use for communicate repository and activity
 */
class StoreViewModel(application: Application) :AndroidViewModel(application){

    private var storeRepository : StoreRepository

    init {
        storeRepository = StoreRepository()
    }

    fun getStoresDetails() : LiveData<Resource<Franquicias>>
    {
        return storeRepository.doCallStoresDetails()
    }
}