package com.varun.myapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlin.math.round
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory

import android.location.Location
import android.location.LocationListener

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Database
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.firebase.database.ktx.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database

import kotlin.coroutines.resume
import kotlin.math.*



private fun haversineDistance(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Double {
    val R = 6371.0 // Radius of the Earth in kilometers
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // Distance in kilometers
}

class placeorderactivity : ComponentActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        val intent = intent
        val amount = intent.getDoubleExtra("money", 0.00)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val context = LocalContext.current
        val locationHelper = LocationHelper(context)
        val addressState = remember { mutableStateOf("Select your address") }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val distance = remember { mutableStateOf(0.0) }









        LaunchedEffect(Unit) {


            locationHelper.getAddressString { address ->
                addressState.value = address ?: "Select your address"
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val calculatedDistance = haversineDistance(
                            location.latitude,
                            location.longitude,
                            21.127590,
                            79.129601
                        )
                        distance.value = calculatedDistance // Update state
                    }
                }

        }

        val text = addressState.value
        placeorder(text, amount,Math.round(distance.value*10.0)/10.0  , Math.round(distance.value * 2*10.0)/10.0)




        }
    }

//    fun showNotification(amount: Double) {
//        val channelId = "order_channel"
//        val notificationId = 1
//
//        // Create a notification channel (required for Android 8.0 and above)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Order Notifications"
//            val descriptionText = "Notifications for order confirmations"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, name, importance).apply {
//                description = descriptionText
//            }
//            val notificationManager: NotificationManager =
//                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val builder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.lo)
//            .setContentTitle("Order Confirmed")
//            .setContentText("Your order of rupees $amount has been confirmed.")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        with(NotificationManagerCompat.from(this)) {
//            notify(notificationId, builder.build())
//        }
//    }

    fun showNotification(amount: Double) {
        val channelId = "order_channel"
        val notificationId = 1

        // Ensure notification channel exists (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Order Notifications"
            val descriptionText = "Notifications for order confirmations"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = (R.color.yeloe)
            }
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create an expanded style for better visibility
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText("Your order of rupees ₹$amount has been confirmed. Thank you for shopping with us!")
            .setBigContentTitle("Order Confirmed")
            .setSummaryText("Order Details")

        // Build the notification
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.lo)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.lo)) // Ensure this icon is a clear image
            .setStyle(bigTextStyle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(R.color.yeloe) // Color for the small icon
            .setAutoCancel(true)
            .setContentIntent(getDefaultIntent())

        // Show the notification
        NotificationManagerCompat.from(this).notify(notificationId, builder.build())
    }

    /**
     * Creates a default pending intent for notification click action
     */
    private fun Context.getDefaultIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java) // Replace with your desired destination
        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }



    @Composable
    fun placeorder(place: String, amount: Double, distance: Double, cost: Double) {
        val viewModel: OrderViewModel = viewModel()

        val totalcost = remember { mutableStateOf(0.0) }
        totalcost.value =amount+cost





        val uploadStatus by remember { mutableStateOf(viewModel.uploadStatus) }
        val Authj: AuthViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
        val context = LocalContext.current
        val todoViewModel: TodoViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Address Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),


                ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = place,
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Confirm Location Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity6::class.java)
                        intent.putExtra("money", amount)
                        context.startActivity(intent)
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),


                ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Confirm Your Location",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            // Cost of Transportation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cost of Transportation:",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
                Text(
                    text = cost.toString(), // Example transportation cost
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            // Total Cost
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Cost:",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
                Text(
                    text = "₹${totalcost.value}", // Total amount calculation
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            // Total Distance
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Distance:",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
                Text(
                    text = distance.toString(), // Example distance
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            // Upload todos to Firebase

            val userId = Authj.getUserId()
            // Confirm Order Button
            Button(
                onClick = {

                        viewModel.addOrderDetails(amount,place)

                    todoViewModel.deleteevery()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) ==
                                    PackageManager.PERMISSION_GRANTED -> {
                                (context as placeorderactivity).showNotification(amount)
                            }
                        }
                    } else {
                        (context as placeorderactivity).showNotification(amount)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Confirm Order",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}