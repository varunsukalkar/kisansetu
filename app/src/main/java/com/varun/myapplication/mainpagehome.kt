package com.varun.myapplication

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun homevala(modifie: Modifier) {

    val scrollState = rememberScrollState()
//    val todoViewModel: TodoViewModel = viewModel()
    Column(

        modifier = modifie
            .background(Color.White)
            .fillMaxSize()

            .verticalScroll(scrollState)

    ) {

//        val varun: productviewmodelforvegetable = viewModel()
//        varun.fetchProductsproductviewmodelvegetable("fruits")
//        vegetableScreen22(varun, todoViewModel)

        banner()

        Spacer(modifier = Modifier.height(5.dp))
        textfile("Top Sellers","Based on Previous ")


        val simple :ProductViewModel= viewModel()
        simple.fetchProducts("vegetable")
        ProductScreen(simple)

        Spacer(modifier = Modifier.height(5.dp))
        textfile("Top Sellers","Based on Previous ")





        val simple2 :ProductViewModel2= viewModel()
        simple2.fetchProducts("fruits")

        ProductScreen22(simple2)



        Spacer(modifier = Modifier.height(5.dp))
        textfile("Top Sellers","Based on Previous ")

        val varun :productviewmodelforgrid= viewModel()
        hit(varun)


        Spacer(modifier = Modifier.height(5.dp))
        textfile("Top Sellers","Based on Previous ")
        val simple3 :ProductViewModel3= viewModel()
        simple3.fetchProducts("rootheards")

        ProductScreen222(simple3)









    }
}
