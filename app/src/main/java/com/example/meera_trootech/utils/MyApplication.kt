package com.example.meera_trootech.utils

import android.app.Application
import android.content.Context

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : MyApplication
 * Description : Use to initialize appcontext and prefs object
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        prefs = if(prefs == null){ Prefs(applicationContext)}else{prefs}
    }

    companion object {
        var appContext: Context? = null
        var prefs :Prefs? = null

    }
}