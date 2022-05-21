package com.example.meera_trootech.presentation.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.meera_trootech.model.Menu
import com.example.meera_trootech.network.Resource

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : MenuViewModel
 * Description : This class use for communicate repository and activity
 */
class MenuViewModel(application: Application) : AndroidViewModel(application){

    private var menuRepository : MenuRepository

    init {
        menuRepository = MenuRepository()
    }

    fun getMenuDetails(apiKey: String): LiveData<Resource<Menu>>
    {
        return menuRepository.doCallMenuDetails(apiKey)
    }
}