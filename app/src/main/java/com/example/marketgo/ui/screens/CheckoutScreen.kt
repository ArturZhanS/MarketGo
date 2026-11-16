package com.example.marketgo.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marketgo.model.BasketItem
import com.example.marketgo.model.Product
import com.example.marketgo.ui.components.CheckoutItemRow
import com.example.marketgo.ui.theme.MarketGoTheme
import com.example.marketgo.viewmodel.MarketViewModel

@Composable
fun CheckoutScreen(
    viewModel: MarketViewModel,
    onBackClick: () -> Unit,
    onContinueShoppingClick: () -> Unit
) {
    val items by viewModel.basket.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val totalCount by viewModel.totalCount.collectAsState()

    var isPurchased by rememberSaveable { mutableStateOf(false) }
    var paidTotal by rememberSaveable { mutableStateOf(0) }
    var paidCount by rememberSaveable { mutableStateOf(0) }

    if (isPurchased) {
        BackHandler { onContinueShoppingClick() }
        PurchaseSuccessContent(
            paidTotal = paidTotal,
            paidCount = paidCount,
            onContinueShoppingClick = onContinueShoppingClick
        )
    } else {
        CheckoutContent(
            items = items,
            totalPrice = totalPrice,
            totalCount = totalCount,
            onBackClick = onBackClick,
            onFinalPurchaseClick = {
                paidTotal = totalPrice
                paidCount = totalCount
                viewModel.completePurchase()
                isPurchased = true
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutContent(
    items: List<BasketItem>,
    totalPrice: Int,
    totalCount: Int,
    onBackClick: () -> Unit,
    onFinalPurchaseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Checkout"
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to basket"
                    )
                }
            },
            windowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.primary
            )
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items, key = { it.product.id }) { item ->
                CheckoutItemRow(item = item)
            }
        }
        Surface(
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
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
                    Text("Items", style = MaterialTheme.typography.bodyLarge)
                    Text("$totalCount", style = MaterialTheme.typography.bodyLarge)
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
                    onClick = onFinalPurchaseClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = "Final purchase",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun PurchaseSuccessContent(
    paidTotal: Int,
    paidCount: Int,
    onContinueShoppingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Purchase successful!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You paid $paidTotal ₸ for $paidCount items.\nThank you for shopping with Market Go!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        Button(
            onClick = onContinueShoppingClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = "Continue shopping",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CheckoutPreview() {
    MarketGoTheme {
        CheckoutContent(
            items = listOf(
                BasketItem(Product(1, "Banana", 650, 1, "🍌"), 2),
                BasketItem(Product(2, "Apple", 800, 1, "🍎"), 1),
                BasketItem(Product(7, "Cola 1L", 700, 3, "🥤"), 3)
            ),
            totalPrice = 4200,
            totalCount = 6,
            onBackClick = {},
            onFinalPurchaseClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PurchaseSuccessPreview() {
    MarketGoTheme {
        PurchaseSuccessContent(
            paidTotal = 4200,
            paidCount = 6,
            onContinueShoppingClick = {}
        )
    }
}