package com.varun.myapplication

import androidx.room.Entity


data class Product(
    val id: String = "",
    val name: String="",
    val price: String="",
    val soldBy: String="",
    val quantity: String="",
    val imageUrl: String="",
    val date: String="",
    val username: String=""

)
  
