package com.example.meera_trootech.model


data class Category(
    val categoryname: String,
    val item: List<Item>
)

data class Item(
    val description: String,
    val itemid: String,
    val itemname: String,
    val price: String,
    var quantity : Int
)
