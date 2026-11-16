package com.example.marketgo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val WELCOME = "welcome"
    const val MAIN = "main"
    const val CATEGORY = "category"
    const val BASKET = "basket"
    const val FAVORITES = "favorites"
    const val PROFILE = "profile"
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Routes.MAIN, "Main", Icons.Filled.Home),
    BottomNavItem(Routes.CATEGORY, "Category", Icons.Filled.Category),
    BottomNavItem(Routes.BASKET, "Basket", Icons.Filled.ShoppingCart),
    BottomNavItem(Routes.FAVORITES, "Favorites", Icons.Filled.Favorite),
    BottomNavItem(Routes.PROFILE, "Profile", Icons.Filled.Person)
)