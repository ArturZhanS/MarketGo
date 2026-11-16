package com.example.marketgo.model

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val categoryId: Int,
    val emoji: String
)