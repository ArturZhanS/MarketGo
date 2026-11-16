package com.example.marketgo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val WELCOME = "welcome"
    const val MAIN = "main"
    const val CATEGORY = "category"
    const val BASKET = "basket"
    const val FAVORITES = "favorites"
    const val PROFILE = "profile"

    const val CATEGORY_PRODUCTS = "category_products"
    const val CATEGORY_ID_ARG = "categoryId"
    fun categoryProducts(categoryId: Int) = "$CATEGORY_PRODUCTS/$categoryId"

    const val CHECKOUT = "checkout"
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Routes.MAIN, "Main", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(Routes.CATEGORY, "Category", Icons.Filled.Category, Icons.Outlined.Category),
    BottomNavItem(Routes.BASKET, "Basket", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
    BottomNavItem(Routes.FAVORITES, "Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    BottomNavItem(Routes.PROFILE, "Profile", Icons.Filled.Person, Icons.Outlined.Person)
)