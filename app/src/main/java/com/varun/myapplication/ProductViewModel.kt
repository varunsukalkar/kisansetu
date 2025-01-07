package com.varun.myapplication


import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await
import okhttp3.internal.userAgent


class ProductViewModel (): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

  //  private val database = FirebaseDatabase.getInstance().getReference("products")


    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProducts(path: String) {
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
class ProductViewModel2 (): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    //  private val database = FirebaseDatabase.getInstance().getReference("products")


    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProducts(path: String) {
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

class ProductViewModel3 (): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    //  private val database = FirebaseDatabase.getInstance().getReference("products")


    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProducts(path: String) {
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




class ProductViewModeldisplay (): ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products



    //  private val database = FirebaseDatabase.getInstance().getReference("products")

    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProducts(path: String) {
        viewModelScope.launch {
            try {
                val snapshot =
                    path.let { database.child("products").get().await() }
                val productList = snapshot?.children?.mapNotNull { it.getValue(Product::class.java) }
                if (productList != null) {
                    _products.value = productList
                }
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
//class ProductViewModel(private val repository: Repository) : ViewModel() {
//    private val _products = MutableStateFlow<List<Product>>(emptyList())
//    val products: StateFlow<List<Product>> = _products
//
//    private val database = FirebaseDatabase.getInstance().reference
//
//    // Function to fetch products from Firebase
//    fun fetchProducts(path: String) {
//        viewModelScope.launch {
//            try {
//                val snapshot = database.child(path).get().await()
//                val productList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
//                _products.value = productList
//            } catch (e: Exception) {
//                e.printStackTrace() // Handle error
//            }
//        }
//    }
//
//    // Save product to Room database
//    fun saveProductToRoom(product: Product) {
//        viewModelScope.launch {
//            try {
//                repository.upsertNote(product) // Save product to Room
//            } catch (e: Exception) {
//                e.printStackTrace() // Handle error
//            }
//        }
//    }
//    val productsFromRoom: StateFlow<List<Product>> = repository.getAllNotes()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//    // Example: Calculate total price of all products
//    fun calculateTotalPrice(): Double {
//        return products.value.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
//    }
//}