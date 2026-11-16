package com.example.marketgo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketgo.model.TemporaryData
import com.example.marketgo.model.Product
import com.example.marketgo.ui.components.MarketSearchBar
import com.example.marketgo.ui.components.ProductCard
import com.example.marketgo.ui.theme.MarketGoTheme
import com.example.marketgo.viewmodel.MarketViewModel

@Composable
fun MainScreen(
    viewModel: MarketViewModel
) {
    val query by viewModel.searchQuery.collectAsState()
    val products by viewModel.filteredProducts.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    MainScreenContent(
        query = query,
        products = products,
        favoriteIds = favoriteIds,
        onQueryChange = viewModel::onSearchQueryChange,
        onFavoriteClick = viewModel::toggleFavorite,
        onAddToBasketClick = viewModel::addToBasket
    )
}

@Composable
fun MainScreenContent(
    query: String,
    products: List<Product>,
    favoriteIds: Set<Int>,
    onQueryChange: (String) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onAddToBasketClick: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Market Go",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        MarketSearchBar(
            query = query,
            onQueryChange = onQueryChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (products.isEmpty()) {
            NotFoundMessage(query = query)
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = product.id in favoriteIds,
                        onFavoriteClick = {
                            onFavoriteClick(product.id)
                        },
                        onAddToBasketClick = {
                            onAddToBasketClick(product)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotFoundMessage(
    query: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.SearchOff,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Product not found",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Nothing matches \"$query\"",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MarketGoTheme {
        MainScreenContent(
            query = "",
            products = TemporaryData.products.take(8),
            favoriteIds = setOf(1, 4),
            onQueryChange = {},
            onFavoriteClick = {},
            onAddToBasketClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainScreenNotFoundPreview() {
    MarketGoTheme {
        MainScreenContent(
            query = "sushi",
            products = emptyList(),
            favoriteIds = emptySet(),
            onQueryChange = {},
            onFavoriteClick = {},
            onAddToBasketClick = {}
        )
    }
}