package com.varun.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class productviewmodelforgrid :
 ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val database = FirebaseDatabase.getInstance().getReference("products")

    // Function to fetch products
    fun fetchProductsss() {
        viewModelScope.launch {
            try {
                val snapshot = database.get().await()
                val productList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                _products.value = productList
                print(productList)
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }}