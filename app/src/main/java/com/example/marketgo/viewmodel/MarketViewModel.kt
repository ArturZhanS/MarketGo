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
}