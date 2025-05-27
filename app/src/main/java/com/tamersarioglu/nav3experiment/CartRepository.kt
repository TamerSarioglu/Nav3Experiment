package com.tamersarioglu.nav3experiment

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object CartRepository {
    private val cartItems = mutableStateMapOf<String, CartItem>()
    
    var totalItemsInCart by mutableStateOf(0)
        private set
    
    fun addToCart(productId: String, productName: String) {
        val currentItem = cartItems[productId]
        if (currentItem != null) {
            cartItems[productId] = currentItem.copy(quantity = currentItem.quantity + 1)
        } else {
            cartItems[productId] = CartItem(productId, productName, 1)
        }
        updateTotalItems()
    }
    
    fun removeFromCart(productId: String) {
        cartItems.remove(productId)
        updateTotalItems()
    }
    
    fun getCartItem(productId: String): CartItem? = cartItems[productId]
    
    fun isInCart(productId: String): Boolean = cartItems.containsKey(productId)
    
    fun getQuantity(productId: String): Int = cartItems[productId]?.quantity ?: 0
    
    private fun updateTotalItems() {
        totalItemsInCart = cartItems.values.sumOf { it.quantity }
    }
    
    fun getAllCartItems(): Map<String, CartItem> = cartItems.toMap()
}

data class CartItem(
    val productId: String,
    val productName: String,
    val quantity: Int
) 