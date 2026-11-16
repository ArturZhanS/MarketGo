package com.example.marketgo.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.marketgo.ui.components.MarketBottomBar
import com.example.marketgo.ui.screens.MainScreen
import com.example.marketgo.ui.screens.PlaceholderScreen
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
            composable(Routes.MAIN) { MainScreen(viewModel) }
            composable(Routes.CATEGORY) { PlaceholderScreen("Category") }
            composable(Routes.BASKET) { PlaceholderScreen("Basket") }
            composable(Routes.FAVORITES) { PlaceholderScreen("Favorites") }
            composable(Routes.PROFILE) { PlaceholderScreen("Profile") }
        }
    }
}