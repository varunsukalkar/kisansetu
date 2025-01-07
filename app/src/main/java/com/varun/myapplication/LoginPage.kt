package com.varun.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//
//@Composable
//fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    val authState = authViewModel.authState.observeAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.Authenticated -> navController.navigate("home")
//            is AuthState.Error -> Toast.makeText(
//                context,
//                (authState.value as AuthState.Error).message,
//                Toast.LENGTH_SHORT
//            ).show()
//            else -> Unit
//        }
//    }
//
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        // Image at the top
//        Image(
//            painter = painterResource(R.drawable.img_3),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//        )
//
//        // Card that overlaps the image
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//                .padding(horizontal = 16.dp)
//
//                .offset(y = 350.dp), // Adjust the overlap
//            shape = RoundedCornerShape(16.dp),
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//
//                    .background(Color.White)
//                ,
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = "Login Page", fontSize = 32.sp)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                OutlinedTextField(
//                    value = email,
//                    onValueChange = { email = it },
//                    label = { Text(text = "Email") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text(text = "Password") },
//                    modifier = Modifier.fillMaxWidth(),
//                    visualTransformation = PasswordVisualTransformation()
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    onClick = { authViewModel.login(email, password) },
//                    enabled = authState.value != AuthState.Loading,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(text = "Login")
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                TextButton(onClick = { navController.navigate("signup") }) {
//                    Text(text = "Don't have an account? Signup")
//                }
//            }
//        }
//    }
//}
@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                val currentUser = authViewModel.getUserId()
                if (currentUser != null) {
                    authViewModel.fetchUserRole(currentUser) // Fetch role directly
                }
            }
            is AuthState.Role -> {
                // Navigate to appropriate page based on the role
                val role = (authState.value as AuthState.Role).role
                if (role == "Farmer") {
                    navController.navigate("uploadfarmer")
                } else {
                    navController.navigate("home")
                }
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        // Image at the top
        Image(
            painter = painterResource(R.drawable.img_3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        // Card that overlaps the image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(horizontal = 16.dp)
                .offset(y = 350.dp), // Adjust the overlap
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login Page", fontSize = 32.sp)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { authViewModel.login(email, password) },
                    enabled = authState.value != AuthState.Loading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Login")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { navController.navigate("signup") }) {
                    Text(text = "Don't have an account? Signup")
                }
            }
        }
    }
}

