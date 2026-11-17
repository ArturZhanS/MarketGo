package com.example.marketgo.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marketgo.ui.components.MarketBottomBar
import com.example.marketgo.ui.screens.BasketScreen
import com.example.marketgo.ui.screens.CategoryProductsScreen
import com.example.marketgo.ui.screens.CategoryScreen
import com.example.marketgo.ui.screens.CheckoutScreen
import com.example.marketgo.ui.screens.FavoritesScreen
import com.example.marketgo.ui.screens.MainScreen
import com.example.marketgo.ui.screens.ProfileScreen
import com.example.marketgo.ui.screens.PurchaseHistoryScreen
import com.example.marketgo.ui.screens.WelcomeScreen
import com.example.marketgo.viewmodel.MarketViewModel

@Composable
fun AppNavigation(
    viewModel: MarketViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = bottomNavItems.any { it.route == currentRoute }
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MarketBottomBar(
                    items = bottomNavItems,
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.MAIN) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.WELCOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.WELCOME) {
                WelcomeScreen(
                    onContinueClick = {
                        navController.navigate(Routes.MAIN) {
                            popUpTo(Routes.WELCOME) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.MAIN) {
                MainScreen(viewModel)
            }
            composable(Routes.CATEGORY) {
                CategoryScreen(
                    categories = viewModel.categories,
                    onCategoryClick = { id -> navController.navigate(Routes.categoryProducts(id)) }
                )
            }
            composable(
                route = "${Routes.CATEGORY_PRODUCTS}/{${Routes.CATEGORY_ID_ARG}}",
                arguments = listOf(navArgument(Routes.CATEGORY_ID_ARG) { type = NavType.IntType })
            ) { entry ->
                val categoryId = entry.arguments?.getInt(Routes.CATEGORY_ID_ARG) ?: 0
                CategoryProductsScreen(
                    viewModel = viewModel,
                    categoryId = categoryId,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Routes.BASKET) {
                BasketScreen(
                    viewModel = viewModel,
                    onBuyClick = { navController.navigate(Routes.CHECKOUT) }
                )
            }
            composable(Routes.CHECKOUT) {
                CheckoutScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onContinueShoppingClick = {
                        navController.popBackStack(Routes.MAIN, inclusive = false)
                    }
                )
            }
            composable(Routes.FAVORITES) {
                FavoritesScreen(viewModel)
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    viewModel = viewModel,
                    onHistoryClick = { navController.navigate(Routes.HISTORY) }
                )
            }
            composable(Routes.HISTORY) {
                PurchaseHistoryScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}