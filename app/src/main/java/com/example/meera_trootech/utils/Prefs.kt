package com.example.meera_trootech.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : Prefs
 * Description : Store application data
 */
class Prefs (context: Context)
{
    private val preferences: SharedPreferences = context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)

    fun setString(aKey: String, aValue: String?) {
        preferences.edit().putString(aKey, aValue).apply()
    }

    fun getString(aKey: String): String? {
        return preferences.getString(aKey, "")
    }
}