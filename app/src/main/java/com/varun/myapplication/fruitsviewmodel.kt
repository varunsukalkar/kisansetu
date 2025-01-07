package com.varun.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class fruitsviewmodel (): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    //  private val database = FirebaseDatabase.getInstance().getReference("products")



    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProductsfruitsviewmodel(path: String) {
        viewModelScope.launch {
            try {
                val snapshot = database.child(path).get().await()
                val productList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                _products.value = productList
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }

    // Example: Calculate total price of all products
    fun calculateTotalPrice(): Double {
        return products.value.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
    }



}