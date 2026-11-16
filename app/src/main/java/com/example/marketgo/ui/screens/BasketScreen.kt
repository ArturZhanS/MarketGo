package com.example.marketgo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketgo.model.BasketItem
import com.example.marketgo.model.Product
import com.example.marketgo.ui.components.BasketItemRow
import com.example.marketgo.ui.theme.MarketGoTheme
import com.example.marketgo.viewmodel.MarketViewModel

@Composable
fun BasketScreen(
    viewModel: MarketViewModel,
    onBuyClick: () -> Unit
) {
    val items by viewModel.basket.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val totalCount by viewModel.totalCount.collectAsState()

    BasketScreenContent(
        items = items,
        totalPrice = totalPrice,
        totalCount = totalCount,
        onPlusClick = viewModel::addToBasket,
        onMinusClick = viewModel::decreaseQuantity,
        onDeleteClick = viewModel::removeFromBasket,
        onBuyClick = onBuyClick
    )
}

@Composable
fun BasketScreenContent(
    items: List<BasketItem>,
    totalPrice: Int,
    totalCount: Int,
    onPlusClick: (Product) -> Unit,
    onMinusClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onBuyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Basket",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 12.dp)
        )

        if (items.isEmpty()) {
            EmptyBasketMessage()
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items, key = { it.product.id }) { item ->
                    BasketItemRow(
                        item = item,
                        onPlusClick = { onPlusClick(item.product) },
                        onMinusClick = { onMinusClick(item.product.id) },
                        onDeleteClick = { onDeleteClick(item.product.id) }
                    )
                }
            }
            BasketSummary(
                totalCount = totalCount,
                totalPrice = totalPrice,
                onBuyClick = onBuyClick
            )
        }
    }
}

@Composable
private fun BasketSummary(
    totalCount: Int,
    totalPrice: Int,
    onBuyClick: () -> Unit
) {
    Surface(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Items",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$totalCount",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$totalPrice ₸",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onBuyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Buy",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun EmptyBasketMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Your basket is empty",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Add something tasty from the Main screen",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BasketScreenPreview() {
    MarketGoTheme {
        BasketScreenContent(
            items = listOf(
                BasketItem(Product(1, "Banana", 650, 1, "🍌"), 2),
                BasketItem(Product(2, "Apple", 800, 1, "🍎"), 1)
            ),
            totalPrice = 2100,
            totalCount = 3,
            onPlusClick = {},
            onMinusClick = {},
            onDeleteClick = {},
            onBuyClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BasketEmptyPreview() {
    MarketGoTheme {
        BasketScreenContent(
            items = emptyList(),
            totalPrice = 0,
            totalCount = 0,
            onPlusClick = {},
            onMinusClick = {},
            onDeleteClick = {},
            onBuyClick = {}
        )
    }
}