package com.varun.myapplication

//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.KeyboardArrowUp
//import androidx.compose.material3.Button
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.unit.toSize
//import androidx.navigation.NavController
//
//
//@Composable
//fun   SignupPage(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    var email by remember {
//        mutableStateOf("")
//    }
//
//    var password by remember {
//        mutableStateOf("")
//    }
//    var name by remember {
//        mutableStateOf("")
//    }
//
//    var mobileno by remember {
//        mutableStateOf("")
//    }
//
//
//
//
//
//
//    val authState = authViewModel.authState.observeAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.Authenticated -> navController.navigate("home")
//            is AuthState.Error -> Toast.makeText(
//                context,
//                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
//            ).show()
//
//            else -> Unit
//        }
//    }
//
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Signup Page", fontSize = 32.sp)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = {
//                email = it
//            },
//            label = {
//                Text(text = "Email")
//            }
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = {
//                password = it
//            },
//            label = {
//                Text(text = "Password")
//            }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = {
//                name = it
//            },
//            label = {
//                Text(text = "full name as of adhar card")
//            }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//
//
//        // Creating a composable function
//// to create an Outlined Text Field
//// Calling this function as content
//// in the above function
//
//
//            // Declaring a boolean value to store
//            // the expanded state of the Text Field
//            var mExpanded by remember { mutableStateOf(false) }
//
//            // Create a list of cities
//            val mCities = listOf("Farmer", "Retailer", "Customer")
//
//            // Create a string value to store the selected city
//            var mSelectedText by remember { mutableStateOf("") }
//
//            var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
//
//            // Up Icon when expanded and down icon when collapsed
//            val icon = if (mExpanded)
//                Icons.Filled.KeyboardArrowUp
//            else
//                Icons.Filled.KeyboardArrowDown
//
//            Column(Modifier.padding(20.dp)) {
//
//                // Create an Outlined Text Field
//                // with icon and not expanded
//                OutlinedTextField(
//                    value = mSelectedText,
//                    onValueChange = { mSelectedText = it },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .onGloballyPositioned { coordinates ->
//                            // This value is used to assign to
//                            // the DropDown the same width
//                            mTextFieldSize = coordinates.size.toSize()
//                        },
//                    label = { Text("Label") },
//                    trailingIcon = {
//                        Icon(icon, "contentDescription",
//                            Modifier.clickable { mExpanded = !mExpanded })
//                    }
//                )
//
//                // Create a drop-down menu with list of cities,
//                // when clicked, set the Text Field text as the city selected
//                DropdownMenu(
//                    expanded = mExpanded,
//                    onDismissRequest = { mExpanded = false },
//                    modifier = Modifier
//                        .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
//                ) {
//                    mCities.forEach { label ->
//                        DropdownMenuItem(text = { Text("Label text") },onClick =
//                        {
//                            mSelectedText = label
//                            mExpanded = false
//
//                        })                    }
//                }
//            }
//
//
//
//        OutlinedTextField(
//            value = mobileno,
//            onValueChange = {
//                mobileno = it
//            },
//            label = {
//                Text(text = "+91 mobile no ")
//            }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//
//
//
//
//        Button(
//            onClick = {
//                authViewModel.signup(email, password,name,mSelectedText,mobileno)
//                email="";
//                password="";
//                name="";
//                mSelectedText="";
//                mobileno="";
//            }, enabled = authState.value != AuthState.Loading
//        ) {
//            Text(text = "Create account")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        TextButton(onClick = {
//            navController.navigate("login")
//        }) {
//            Text(text = "Already have an account, Login")
//        }
//
//    }
//}



import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
//
//@Composable
//fun SignupPage(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    // State variables
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var name by remember { mutableStateOf("") }
//    var mobileno by remember { mutableStateOf("") }
//    var mSelectedText by remember { mutableStateOf("") }
//    var mExpanded by remember { mutableStateOf(false) }
//    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
//
//    val authState = authViewModel.authState.observeAsState()
//    val context = LocalContext.current
//
//    // Handle authentication state
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
//    // Design the Signup Page
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Title
//        Text(
//            text = "Signup Page",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.primary
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Input fields
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text(text = "Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text(text = "Password") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text(text = "Full Name (As on Aadhaar Card)") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Drop-down menu for selecting role
//        OutlinedTextField(
//            value = mSelectedText,
//            onValueChange = { mSelectedText = it },
//            label = { Text("Select Role") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    mTextFieldSize = coordinates.size.toSize()
//                },
//            trailingIcon = {
//                val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    Modifier.clickable { mExpanded = !mExpanded }
//                )
//            }
//        )
//        DropdownMenu(
//            expanded = mExpanded,
//            onDismissRequest = { mExpanded = false },
//            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
//        ) {
//            listOf("Farmer", "Retailer", "Customer").forEach { role ->
//                DropdownMenuItem(
//                    text = { Text(role) },
//                    onClick = {
//                        mSelectedText = role
//                        mExpanded = false
//                    }
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = mobileno,
//            onValueChange = { mobileno = it },
//            label = { Text(text = "+91 Mobile No") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Signup button
//        Button(
//            onClick = {
//                authViewModel.signup(email, password, name, mSelectedText, mobileno)
//                email = ""
//                password = ""
//                name = ""
//                mSelectedText = ""
//                mobileno = ""
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            shape = RoundedCornerShape(8.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
//        ) {
//            Text(text = "Create Account", color = Color.White, fontSize = 16.sp)
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Navigate to login
//        TextButton(onClick = { navController.navigate("login") }) {
//            Text(text = "Already have an account? Login", color = MaterialTheme.colorScheme.secondary)
//        }
//    }
//}
//@Composable
//fun SignupPage(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    authViewModel: AuthViewModel
//) {
//    // State variables
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var name by remember { mutableStateOf("") }
//    var mobileno by remember { mutableStateOf("") }
//    var mSelectedText by remember { mutableStateOf("") }
//    var mExpanded by remember { mutableStateOf(false) }
//    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
//
//    val authState = authViewModel.authState.observeAsState()
//    val context = LocalContext.current
//
//    // Handle authentication state
//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.Authenticated -> {
//                navController.navigate("home") // Navigate to home after successful signup
//            }
//            is AuthState.Error -> {
//                Toast.makeText(
//                    context,
//                    (authState.value as AuthState.Error).message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            else -> Unit
//        }
//    }
//
//    // Design the Signup Page
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Title
//        Text(
//            text = "Signup Page",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.primary
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Input fields
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text(text = "Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text(text = "Password") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text(text = "Full Name (As on Aadhaar Card)") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Drop-down menu for selecting role
//        OutlinedTextField(
//            value = mSelectedText,
//            onValueChange = { mSelectedText = it },
//            label = { Text("Select Role") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates -> mTextFieldSize = coordinates.size.toSize() },
//            trailingIcon = {
//                val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    Modifier.clickable { mExpanded = !mExpanded }
//                )
//            }
//        )
//        DropdownMenu(
//            expanded = mExpanded,
//            onDismissRequest = { mExpanded = false },
//            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
//        ) {
//            listOf("Farmer", "Retailer", "Customer").forEach { role ->
//                DropdownMenuItem(
//                    text = { Text(role) },
//                    onClick = {
//                        mSelectedText = role
//                        mExpanded = false
//                    }
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = mobileno,
//            onValueChange = { mobileno = it },
//            label = { Text(text = "+91 Mobile No") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Signup button
//        Button(
//            onClick = {
//                // Check if required fields are filled
//                if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && mSelectedText.isNotBlank() && mobileno.isNotBlank()) {
//                    authViewModel.signup(email, password, name, mSelectedText, mobileno)
//                    // Optionally, clear fields after signup if needed
//                    // email = ""
//                    // password = ""
//                    // name = ""
//                    // mSelectedText = ""
//                    // mobileno = ""
//                } else {
//                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            shape = RoundedCornerShape(8.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
//        ) {
//            Text(text = "Create Account", color = Color.White, fontSize = 16.sp)
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Navigate to login
//        TextButton(onClick = { navController.navigate("login") }) {
//            Text(text = "Already have an account? Login", color = MaterialTheme.colorScheme.secondary)
//        }
//    }
//}
@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    // State variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var mobileno by remember { mutableStateOf("") }
    var mSelectedText by remember { mutableStateOf("") }
    var mExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // Handle authentication state changes and navigate after successful signup
    // Handle authentication state
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Role -> {
                val role = (authState.value as AuthState.Role).role
                when (role) {
                    "Farmer" -> {
                        navController.navigate("uploadfarmer") // Navigate to upload screen for farmers
                    }
                    else -> {
                        navController.navigate("home") // Navigate to home screen for other roles
                    }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    // Design the Signup Page UI
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Signup Page",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Input fields
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Full Name (As on Aadhaar Card)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Drop-down menu for selecting role
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            label = { Text("Select Role") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> mTextFieldSize = coordinates.size.toSize() },
            trailingIcon = {
                val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    Modifier.clickable { mExpanded = !mExpanded }
                )
            }
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            listOf("Farmer", "Retailer", "Customer").forEach { role ->
                DropdownMenuItem(
                    text = { Text(role) },
                    onClick = {
                        mSelectedText = role
                        mExpanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mobileno,
            onValueChange = { mobileno = it },
            label = { Text(text = "+91 Mobile No") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Signup button
        Button(
            onClick = {
                // Check if required fields are filled
                if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && mSelectedText.isNotBlank() && mobileno.isNotBlank()) {
                    authViewModel.signup(email, password, name, mSelectedText, mobileno)
                    // Optionally, clear fields after signup if needed
                    // email = ""
                    // password = ""
                    // name = ""
                    // mSelectedText = ""
                    // mobileno = ""
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Create Account", color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Navigate to login
        TextButton(onClick = { navController.navigate("login") }) {
            Text(text = "Already have an account? Login", color = MaterialTheme.colorScheme.secondary)
        }
    }
}
