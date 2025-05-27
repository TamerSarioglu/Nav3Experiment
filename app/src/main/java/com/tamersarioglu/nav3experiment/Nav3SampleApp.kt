package com.tamersarioglu.nav3experiment

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nav3SampleApp() {
    val backStack = rememberNavBackStack(AppRoute.Home)
    val navTo: (AppRoute) -> Unit = { backStack.add(it) }
    val goBack: () -> Unit = { if (backStack.size > 1) backStack.removeLastOrNull() }
    val currentRoute = backStack.lastOrNull() ?: AppRoute.Home

    Column {
        TopAppBar(
            title = {
                Text(
                    text = when (currentRoute) {
                        is AppRoute.Home -> "Home"
                        is AppRoute.ProductList -> "Product List"
                        is AppRoute.ProductDetail -> "Product Detail: ${currentRoute.productName}"
                        else -> {
                            "Nav3 Sample App"
                        }
                    }
                )
            },
            navigationIcon = {
                if (backStack.size > 1) {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            },
            actions = {
                if (CartRepository.totalItemsInCart > 0) {
                    Text(
                        text = "Cart: ${CartRepository.totalItemsInCart}",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        )

        NavDisplay(
            backStack = backStack,
            onBack = { steps -> repeat(steps) { goBack() } },
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = {
                slideInHorizontally(animationSpec = tween(300)) + fadeIn() togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300)
                        ) + fadeOut()
            },
            popTransitionSpec = {
                slideInHorizontally(animationSpec = tween(300)) + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(300)) + fadeOut()
            },
            entryProvider = entryProvider {
                entry<AppRoute.Home> {
                    HomeScreen(onNavigateToProducts = { navTo(AppRoute.ProductList) })
                }
                entry<AppRoute.ProductList> {
                    ProductListScreen(
                        onProductClick = { productId, productName ->
                            navTo(AppRoute.ProductDetail(productId, productName))
                        }
                    )
                }
                entry<AppRoute.ProductDetail> { route ->
                    val viewModel: ProductDetailViewModel = viewModel(
                        factory = ProductDetailViewModelFactory(
                            productId = route.productId,
                            productName = route.productName
                        )
                    )
                    ProductDetailScreen(
                        productId = route.productId,
                        productName = route.productName,
                        viewModel = viewModel
                    )
                }
            }
        )
    }
}