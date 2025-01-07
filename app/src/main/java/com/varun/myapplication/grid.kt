package com.varun.myapplication

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun hit(productViewModel: productviewmodelforgrid = viewModel()){
    val products22 by productViewModel.products.collectAsState()
    val cellcount = 3;
    LaunchedEffect(Unit) {
        productViewModel.fetchProductsss()
    }

    LazyVerticalGrid(
        modifier = Modifier.height(950.dp),
        columns = GridCells.Fixed(cellcount),
        horizontalArrangement = Arrangement.spacedBy(10.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 30.dp, top = 10.dp, start = 5.dp),


    ) {
        itemsIndexed( products22, itemContent = { index, items ->

            cat(items)
        })

    }
}





@Composable
fun cat(product:Product){
    val context = LocalContext.current
    Box(modifier=Modifier, contentAlignment = Alignment.Center) {
        Column(


        ) {
            Card(
                modifier = Modifier.width(100.dp).height(130.dp).clickable {
                    context.startActivity(Intent(context,MainActivity2::class.java))
                }
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true) // Smooth transition
                       // .placeholder(R.drawable.banana) // Your placeholder drawable
                      //  .error(R.drawable.banana) // Your error drawable
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