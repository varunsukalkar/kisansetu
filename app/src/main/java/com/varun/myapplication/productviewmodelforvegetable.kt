package com.varun.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class productviewmodelforvegetable: ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products


    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProductsproductviewmodelvegetable(path: String) {
        viewModelScope.launch {
            try {
                val snapshot = database.child(path).get().await()
                val productList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                _products.value = productList
                _filteredProducts.value = productList
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }
    fun filterProducts(query: String) {
        viewModelScope.launch {
            _filteredProducts.value = if (query.isEmpty()) {
                _products.value // Return all products if query is empty
            } else {
                _products.value.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
    }
}



class productviewmodelforfruits: ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products


    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProductsproductviewmodelfruits(path: String) {
        viewModelScope.launch {
            try {
                val snapshot = database.child(path).get().await()
                val productList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                _products.value = productList
                _filteredProducts.value = productList
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }
    fun filterProducts(query: String) {
        viewModelScope.launch {
            _filteredProducts.value = if (query.isEmpty()) {
                _products.value // Return all products if query is empty
            } else {
                _products.value.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
    }
}

@Composable
fun SearchBarfruits(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search by name...") },
        singleLine = true
    )
}

@Composable
fun fruitsScreen22(
    productViewModel: productviewmodelforfruits = viewModel(),
    todoViewModel: TodoViewModel= viewModel()
) {
    val products by productViewModel.products.collectAsState()
    val query = remember { mutableStateOf("") }
    val filteredProducts by productViewModel.filteredProducts.collectAsState()

    Column {
        SearchBarfruits(
            query = query.value,
            onQueryChange = {
                query.value = it
                productViewModel.filterProducts(it)
            }
        )
        if (filteredProducts.isEmpty()) {
            Text(
                text = "No products found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {


            val cellcount = 2;


            LazyVerticalGrid(
                modifier = Modifier.height(900.dp),
                columns = GridCells.Fixed(cellcount),
                horizontalArrangement = Arrangement.spacedBy(10.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp),



                )

           {
                itemsIndexed(filteredProducts) { _, product ->
                    symbolfruits(product = product, todoViewModel)
                }
            }
        }
    }
}

@Composable
fun symbolfruits(product: Product, todoViewModel: TodoViewModel) {
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)

            )
            .width(150.dp)
            .height(300.dp)
            .padding(8.dp, 8.dp)


    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .fillMaxSize()
                .background(Color.White)
                .wrapContentSize(Alignment.Center)


        ) {
            Card(
                modifier = Modifier
                    .width(140.dp)
                    .height(140.dp)

            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true) // Smooth transition
//                        .placeholder(R.drawable.banana) // Your placeholder drawable
//                        .error(R.drawable.banana) // Your error drawable
                        .build(),
                    contentDescription = "Image from URL",
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .fillMaxHeight(),

                    contentScale = ContentScale.Crop
                )

            }
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,


                )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Rate : " + product.quantity,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Price : " + product.price,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 15.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Card(
                    Modifier
                        .background(Color.White)
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .height(30.dp)
                        .width(30.dp)


                ) {

                    var img: Painter = painterResource(id = R.drawable.redhead)

                    Image(
                        painter = img,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),

                        contentScale = ContentScale.Crop,


                        )

                }
                Spacer(modifier = Modifier.width(5.dp))
                Card(
                    Modifier
                        .background(Color.White)
                        .width(100.dp)
                        .height(40.dp), elevation = CardDefaults.cardElevation(5.dp)


                )
                {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .clickable {
                                todoViewModel.addTodo(
                                    product.id,
                                    product.name,
                                    product.price,
                                    product.soldBy,
                                    product.quantity,
                                    product.imageUrl,
                                    product.date,
                                    product.username
                                )
                            }


                    ) {
                        Text(
                            text = "Add",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Green


                        )

                    }
                }


            }

        }
    }
}



class productviewmodelforheadbs: ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products


    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    private val database = FirebaseDatabase.getInstance().reference

    // Function to fetch products from a specific path
    fun fetchProductsproductviewmodelheadbs(path: String) {
        viewModelScope.launch {
            try {
                val snapshot = database.child(path).get().await()
                val productList = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                _products.value = productList
                _filteredProducts.value = productList
            } catch (e: Exception) {
                e.printStackTrace() // Handle error
            }
        }
    }
    fun filterProducts(query: String) {
        viewModelScope.launch {
            _filteredProducts.value = if (query.isEmpty()) {
                _products.value // Return all products if query is empty
            } else {
                _products.value.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
    }
}

@Composable
fun SearchBarhrarbs(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search by name...") },
        singleLine = true
    )
}

@Composable
fun hearbsScreen22(
    productViewModel: productviewmodelforheadbs= viewModel(),
    todoViewModel: TodoViewModel= viewModel()
) {
    val products by productViewModel.products.collectAsState()
    val query = remember { mutableStateOf("") }
    val filteredProducts by productViewModel.filteredProducts.collectAsState()

    Column {
        SearchBarhrarbs (
            query = query.value,
            onQueryChange = {
                query.value = it
                productViewModel.filterProducts(it)
            }
        )
        if (filteredProducts.isEmpty()) {
            Text(
                text = "No products found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {


            val cellcount = 2;


            LazyVerticalGrid(
                modifier = Modifier.height(900.dp),
                columns = GridCells.Fixed(cellcount),
                horizontalArrangement = Arrangement.spacedBy(10.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp),



                )

            {
                itemsIndexed(filteredProducts) { _, product ->
                    symbolfruits(product = product, todoViewModel)
                }
            }
        }
    }
}
