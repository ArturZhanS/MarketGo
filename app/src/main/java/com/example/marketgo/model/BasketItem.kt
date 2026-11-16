package com.example.marketgo.model

data class BasketItem(
    val product: Product,
    val quantity: Int
) {
    val totalPrice: Int get() = product.price * quantity
}