package com.varun.myapplication

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle


@Composable
fun TodoListPage(viewModel: TodoViewModel, mod: Modifier = Modifier) {
    val todoList by viewModel.todoList.observeAsState(emptyList())
    val totalCostState = remember { mutableStateOf(0.0) }

    // Calculate the initial total cost
    LaunchedEffect(todoList) {
        totalCostState.value = todoList.sumOf {
            val quantity = (it.quantity.toIntOrNull() ?: 0).coerceAtLeast(1)
            val price = (it.price.toDoubleOrNull() ?: 0.0) / 10 // Adjust the scale
            quantity * price
        }
    }

    Column(
        modifier = mod
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (todoList.isEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No items yet",
                fontSize = 16.sp,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(todoList) { _, item ->
                    Symbol(
                        product = item,
                        onQuantityChange = { updatedCost ->
                            totalCostState.value += updatedCost
                        },
                        onDelete = { viewModel.deleteTodo(item.roomid) }
                    )
                }
            }
        }

        BottomSection(totalCostState.value)
    }
}

@Composable
fun Symbol(product: Todo, onQuantityChange: (Double) -> Unit, onDelete: () -> Unit) {
    val context = LocalContext.current
    val availableQuantity = product.quantity.toIntOrNull() ?: 0
    val minQuantity = (availableQuantity * 0.1).toInt().coerceAtLeast(1)
    val quantityState = remember { mutableStateOf(minQuantity) }

    val pricePerItem = product.price.toDoubleOrNull() ?: 0.0

    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Product Details
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Product : ${product.name}",
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    text = "Quantity Available: $availableQuantity",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = "Price: ₹${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                // Quantity Selector
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    // Minus Button
                    IconButton(
                        onClick = {
                            if (quantityState.value > minQuantity) {
                                val oldQuantity = quantityState.value
                                quantityState.value--
                                onQuantityChange((quantityState.value - oldQuantity) * pricePerItem)
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Decrease Quantity")
                    }

                    // Quantity Input
                    TextField(
                        value = quantityState.value.toString(),
                        onValueChange = { value ->
                            val enteredValue = value.toIntOrNull() ?: minQuantity
                            val clampedValue = enteredValue.coerceIn(minQuantity, availableQuantity)
                            val oldQuantity = quantityState.value
                            quantityState.value = clampedValue
                            onQuantityChange((clampedValue - oldQuantity) * pricePerItem)
                        },
                        modifier = Modifier
                            .width(60.dp)
                            .height(
                                80.dp
                            )
                            .background(Color.White),
                        textStyle = TextStyle(textAlign = TextAlign.Center)
                    )

                    // Plus Button
                    IconButton(
                        onClick = {
                            if (quantityState.value < availableQuantity) {
                                val oldQuantity = quantityState.value
                                quantityState.value++

                                onQuantityChange((quantityState.value - oldQuantity) * (pricePerItem  ))
                            }
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                    }
                }
            }
   
            IconButton(onClick = onDelete) {
                Icon(
                     (Icons.Default.Delete),
                    contentDescription = "Delete",


                )
            }
        }
    }
}




@Composable
fun BottomSection(totalCost: Double) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total Cost: ₹%.2f".format(totalCost),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val intent = Intent(context, placeorderactivity::class.java)
                intent.putExtra("money", totalCost)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)

        ) {
            Text(
                text = "Place Order",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}
