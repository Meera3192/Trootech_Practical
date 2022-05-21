package com.example.meera_trootech.network

/**
 * Created by Meera Bhadania on 21/5/22.
 * Class Name : Resource
 * Description : Handle API response using this class
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}