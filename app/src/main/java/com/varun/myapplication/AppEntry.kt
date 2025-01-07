package com.varun.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
@Composable
fun AppEntry(navController: NavController, authViewModel: AuthViewModel) {
    val authState by authViewModel.authState.observeAsState()

    // LaunchedEffect to navigate based on authentication state
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> {
                // Navigate to the login screen if the user is unauthenticated
                navController.navigate("login") {
                    popUpTo(0) // Clear back stack to prevent navigating back to this screen
                    launchSingleTop = true
                }
            }
            is AuthState.Authenticated -> {
                // Navigate to the default home screen after successful authentication
                navController.navigate("home") {
                    popUpTo(0) // Clear back stack
                    launchSingleTop = true
                }
            }
            is AuthState.Role -> {
                // Check the role and navigate accordingly
                when (val role = (authState as AuthState.Role).role) {
                    "Farmer" -> {
                        // Navigate to uploadfarmer screen if the role is "Farmer"
                        navController.navigate("uploadfarmer") {
                            popUpTo(0) // Clear back stack
                            launchSingleTop = true
                        }
                    }
                    else -> {
                        // If the role is not "Farmer", navigate to the home screen
                        navController.navigate("home") {
                            popUpTo(0) // Clear back stack
                            launchSingleTop = true
                        }
                    }
                }
            }
            is AuthState.Error -> {
                // Handle error state if authentication failed
                Toast.makeText(navController.context, "Authentication Error", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    // Loading state: Displaying a loading spinner while fetching user role or authentication data
    if (authState is AuthState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
