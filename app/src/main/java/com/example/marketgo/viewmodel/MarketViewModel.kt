package com.example.marketgo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketgo.model.Category
import com.example.marketgo.model.TemporaryData
import com.example.marketgo.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import com.example.marketgo.model.BasketItem
import kotlinx.coroutines.flow.map

class MarketViewModel : ViewModel() {

    private val _products = MutableStateFlow(TemporaryData.products)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    val categories: List<Category> = TemporaryData.categories

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredProducts: StateFlow<List<Product>> =
        combine(_products, _searchQuery) { list, query ->
            val text = query.trim()
            if (text.isEmpty()) list
            else list.filter { it.name.contains(text, ignoreCase = true) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TemporaryData.products
        )

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun toggleFavorite(productId: Int) {
        _favoriteIds.update { ids ->
            if (productId in ids) ids - productId else ids + productId
        }
    }

    fun getCategory(id: Int): Category? = categories.find { it.id == id }

    fun getProductsByCategory(categoryId: Int): List<Product> =
        _products.value.filter { it.categoryId == categoryId }

    private val _basket = MutableStateFlow<List<BasketItem>>(emptyList())
    val basket: StateFlow<List<BasketItem>> = _basket.asStateFlow()

    val totalPrice: StateFlow<Int> = _basket
        .map { items -> items.sumOf { it.totalPrice } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalCount: StateFlow<Int> = _basket
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun addToBasket(product: Product) {
        _basket.update { items ->
            val existing = items.find { it.product.id == product.id }
            if (existing == null) {
                items + BasketItem(product, 1)
            } else {
                items.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
            }
        }
    }

    fun decreaseQuantity(productId: Int) {
        _basket.update { items ->
            items.mapNotNull { item ->
                when {
                    item.product.id != productId -> item
                    item.quantity > 1 -> item.copy(quantity = item.quantity - 1)
                    else -> null
                }
            }
        }
    }

    fun removeFromBasket(productId: Int) {
        _basket.update { items -> items.filter { it.product.id != productId } }
    }

    private val _purchaseHistory = MutableStateFlow<List<BasketItem>>(emptyList())
    val purchaseHistory: StateFlow<List<BasketItem>> = _purchaseHistory.asStateFlow()

    fun completePurchase() {
        _purchaseHistory.update { history -> history + _basket.value }
        _basket.value = emptyList()
    }
}