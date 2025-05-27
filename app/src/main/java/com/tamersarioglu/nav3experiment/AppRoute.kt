package com.tamersarioglu.nav3experiment

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object Home : AppRoute
    
    @Serializable
    data object ProductList : AppRoute
    
    @Serializable
    data class ProductDetail(
        val productId: String, 
        val productName: String
    ) : AppRoute
}