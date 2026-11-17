package com.example.marketgo.model

data class Order(
    val id: Int,
    val items: List<BasketItem>,
    val createdAt: Long
) {
    val totalPrice: Int get() = items.sumOf { it.totalPrice }
    val totalCount: Int get() = items.sumOf { it.quantity }
}