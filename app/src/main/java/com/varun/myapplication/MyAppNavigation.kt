package com.varun.myapplication

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

//    NavHost(navController = navController, startDestination = "login", builder = {
//
//        composable("login"){
//            LoginPage(modifier,navController,authViewModel)
//        }
//        composable("signup"){
//            SignupPage(modifier,navController,authViewModel)
//        }
//        composable("home"){
//           HomePage(navController,authViewModel)
//
//        }
//        composable("uploadfarmer") { FormScreen("fruits") }
//        composable("hhh") { ScrollableTopBar(authViewModel) }
//    })
    NavHost(navController = navController, startDestination = "appEntry") {
        composable("appEntry") { AppEntry(navController, authViewModel) }
        composable("login") { LoginPage(modifier,navController, authViewModel) }
        composable("home") { HomePage(navController, authViewModel) }
        composable("uploadfarmer") { FormScreen("fruits",navController,authViewModel) }
                composable("signup"){
            SignupPage(modifier,navController,authViewModel)
        }
    }
//    NavHost(
//        navController = navController,
//        startDestination = "login"
//    ) {
//        composable("login") {
//            LoginPage(modifier, navController, authViewModel)
//        }
//        composable("signup") {
//            SignupPage(modifier, navController, authViewModel)
//        }
//        composable("home") {
//            HomePage(modifier, navController, authViewModel)
//        }
    }
