package com.varun.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ProductScreen(productViewModel: ProductViewModel = viewModel()) {
    val products by productViewModel.products.collectAsState()



    LazyRow (
        modifier = Modifier.padding(10.dp,5.dp,5.dp,10.dp),
        horizontalArrangement =Arrangement.spacedBy(5.dp)


    ) {
        itemsIndexed( products, itemContent = { index, items ->
            print(items)
            categories(items)
        })


    }
}




@Composable
fun categories(product:Product){
    Box(modifier=Modifier.background(Color.White), contentAlignment = Alignment.Center) {
        Column(


        ) {
            Card(
                modifier = Modifier.width(100.dp).height(130.dp).background(Color.White)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true) // Smooth transition
                        .placeholder(R.drawable.banana) // Your placeholder drawable
                        .error(R.drawable.banana) // Your error drawable
                        .build(),
                    contentDescription = "Image from URL",
                    modifier = Modifier.fillMaxWidth().weight(0.8f),

                    contentScale = ContentScale.Crop
                )

                Text(
                    product.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    modifier=Modifier.fillMaxWidth().weight(0.2f)
                )

            }
        }
    }
}