package com.varun.myapplication


data class Order(
    val amount: Double = 0.0,
    val date: String = "",
    val time: String = "",
    val dayOfWeek: String = "",
    val location :String=""
)