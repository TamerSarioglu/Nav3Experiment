package com.tamersarioglu.nav3experiment

sealed class AppRoute {
    data object Home : AppRoute()
    data object ProductList : AppRoute()
    data class ProductDetail(val productId: String, val productName: String) : AppRoute()
}