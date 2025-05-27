package com.tamersarioglu.nav3experiment

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel

class ProductDetailViewModel(
    private val productId: String,
    private val productName: String
) : ViewModel() {
    
    val addToCartCount by derivedStateOf { 
        CartRepository.getQuantity(productId)
    }
    
    val isInCart by derivedStateOf { 
        CartRepository.isInCart(productId)
    }
    
    fun onAddToCart() {
        CartRepository.addToCart(productId, productName)
        Log.d("ProductDetailVM", "Product $productName added to cart. Total quantity: $addToCartCount")
    }
    
    fun onRemoveFromCart() {
        CartRepository.removeFromCart(productId)
        Log.d("ProductDetailVM", "Product $productName removed from cart")
    }

    init {
        Log.d("ProductDetailVM", "ProductDetailViewModel init for product: $productName")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ProductDetailVM", "ProductDetailViewModel cleared for product: $productName")
    }
} 