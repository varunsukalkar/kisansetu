package com.varun.myapplication

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Function to fetch products
suspend fun fetchProducts(): List<Product> = coroutineScope {
    val database = FirebaseDatabase.getInstance().getReference("products")
    val productList = mutableListOf<Product>()

    // Launch a coroutine for fetching data
    val job = launch {
        try {
            val snapshot = database.get().await() // Await the database result
            snapshot.children.forEach { childSnapshot ->
                val product = childSnapshot.getValue(Product::class.java)
                product?.let { productList.add(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace() // Handle exceptions
        }
    }

    job.join() // Ensure the coroutine completes before returning
    return@coroutineScope productList
}