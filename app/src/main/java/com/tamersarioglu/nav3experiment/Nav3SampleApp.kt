package com.tamersarioglu.nav3experiment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nav3SampleApp() {

    val backStack = remember { mutableStateListOf<AppRoute>(AppRoute.Home) }
    val currentRoute = backStack.lastOrNull() ?: AppRoute.Home

    Column {
        TopAppBar(
            title = {
                Text(
                    text = when (currentRoute) {
                        is AppRoute.Home -> "Home"
                        is AppRoute.ProductList -> "Product List"
                        is AppRoute.ProductDetail -> "Product Detail: ${currentRoute.productName}"
                    }
                )
            },
            navigationIcon = {
                if (backStack.size > 1) {
                    IconButton(onClick = {
                        backStack.removeLastOrNull()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        )

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { route ->
                when (route) {
                    is AppRoute.Home -> NavEntry(route) {
                        HomeScreen(
                            onNavigateToProducts = {
                                backStack.add(AppRoute.ProductList)
                            }
                        )
                    }
                    is AppRoute.ProductList -> NavEntry(route) {
                        ProductListScreen(
                            onProductClick = { productId, productName ->
                                backStack.add(AppRoute.ProductDetail(productId, productName))
                            }
                        )
                    }
                    is AppRoute.ProductDetail -> NavEntry(route) {
                        ProductDetailScreen(
                            productId = route.productId,
                            productName = route.productName
                        )
                    }
                }
            }
        )
    }
}