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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import com.example.marketgo.model.Product
import com.example.marketgo.ui.components.ProductCard
import com.example.marketgo.ui.theme.MarketGoTheme
import com.example.marketgo.viewmodel.MarketViewModel

@Composable
fun FavoritesScreen(
    viewModel: MarketViewModel
) {
    val products by viewModel.favoriteProducts.collectAsState()

    FavoritesScreenContent(
        products = products,
        onFavoriteClick = viewModel::toggleFavorite,
        onAddToBasketClick = viewModel::addToBasket
    )
}

@Composable
fun FavoritesScreenContent(
    products: List<Product>,
    onFavoriteClick: (Int) -> Unit,
    onAddToBasketClick: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (products.isEmpty()) {
            EmptyFavoritesMessage()
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
                        isFavorite = true,
                        onFavoriteClick = { onFavoriteClick(product.id) },
                        onAddToBasketClick = { onAddToBasketClick(product) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoritesMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "No favorites yet",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tap the heart on any product to save it here",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun FavoritesScreenPreview() {
    MarketGoTheme {
        FavoritesScreenContent(
            products = listOf(
                Product(1, "Banana", 650, 1, "🍌"),
                Product(7, "Cola 1L", 700, 3, "🥤"),
                Product(13, "Bread", 180, 5, "🍞")
            ),
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
fun FavoritesEmptyPreview() {
    MarketGoTheme {
        FavoritesScreenContent(
            products = emptyList(),
            onFavoriteClick = {},
            onAddToBasketClick = {}
        )
    }
}