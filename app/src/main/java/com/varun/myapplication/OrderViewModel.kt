package com.varun.myapplication


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database

    var uploadStatus by mutableStateOf<String?>(null)
        private set

    fun addOrderDetails(amount: Double,place:String) {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            val orderRef = database.getReference("users").child(userId).child("orderHistory")

            // Get current date and time
            val currentTimeMillis = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())

            val date = dateFormat.format(Date(currentTimeMillis))
            val time = timeFormat.format(Date(currentTimeMillis))
            val dayOfWeek = dayOfWeekFormat.format(Date(currentTimeMillis))

            // Prepare order details
            val orderDetails = mapOf(
                "amount" to amount,
                "date" to date,
                "time" to time,
                "dayOfWeek" to dayOfWeek,
                "location" to place
            )

            // Push order details to Firebase
            viewModelScope.launch {
                orderRef.push().setValue(orderDetails).addOnSuccessListener {
                    uploadStatus = "Order details uploaded successfully!"
                }.addOnFailureListener { error ->
                    uploadStatus = "Failed to upload order details: ${error.message}"
                }
            }
        } else {
            uploadStatus = "No user is signed in."
        }
    }
}