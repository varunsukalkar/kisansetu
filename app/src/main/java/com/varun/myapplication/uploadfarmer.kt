package com.varun.myapplication
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import com.google.firebase.storage.FirebaseStorage
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(path:String, navControllerv: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navControllerv.navigate("login")

            else-> Unit
        }

    }
    val context = LocalContext.current
    val locationHelper = LocationHelper(context)
    val addressState = remember { mutableStateOf("Select your address") }

    LaunchedEffect(Unit) {
        locationHelper.getAddressString { address ->
            addressState.value = address ?: "Select your address"
        }
    }
    val navController = rememberNavController()


    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState= rememberScrollState()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(0.dp, 0.dp,0.dp, 30.dp),
        topBar = {
            TopAppBar(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF49A3C6),

                        ),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.blue),
                    titleContentColor = Color.Black

                ),
                title = {
                    Column {
                        Modifier
                            .background(
                                color = Color(0xFF49A3C6),
                                shape = RoundedCornerShape(8.dp)
                            )
                        // App Name
                        Text(
                            text = "Kisan Setu",
                            color=Color.Black,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1, // Prevents overflow
                            overflow = TextOverflow.Ellipsis,


                            )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Location Section
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = addressState.value,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Arrow Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Logout Icon",
                            tint = Color.White
                        )
                    }
                },

                scrollBehavior = scrollBehavior
            )



        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },

        content = { innerPadding ->

            NavHostContainer(path,navController, innerPadding,authViewModel,Modifier.padding(innerPadding) )



        }
    )
}




@Composable
fun uploaddd(path: String,paddingValues: PaddingValues) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var soldBy by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val scrollState= rememberScrollState()
    // Firebase references
    val storage = FirebaseStorage.getInstance()
    val database = FirebaseDatabase.getInstance().getReference(path)

    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(paddingValues),
     //   verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(16.dp,25.dp,16.dp,0.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                   ,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Input Fields
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = soldBy,
                    onValueChange = { soldBy = it },
                    label = { Text("Sold By") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity Available") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Image Selection Field
                CustomInputFieldContainer(label = "Select Image") {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Show the selected image if available
                            bitmap?.let { bmp ->
                                Image(
                                    bitmap = bmp.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.size(200.dp)
                                )
                            }

                            // Button to select image
                            Button(onClick = { launcher.launch("image/*") }) {
                                Row(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "Add Photo")
                                }
                            }
                        }
                    }
                }

                // Submit Button
                Button(
                    onClick = {
                        if (name.isNotEmpty() && price.isNotEmpty() && soldBy.isNotEmpty() && quantity.isNotEmpty() && imageUri != null) {
                            uploadImageToFirebase(
                                imageUri!!,
                                storage,
                                database,
                                name,
                                price,
                                soldBy,
                                quantity,
                                context
                            )
                        } else {
                            Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}




@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(

        // set background color
        backgroundColor = Color(0xFF0F9D58)) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },

                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
    }
}
@Composable
fun CustomInputFieldContainer(label: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall
        )
        content()
    }
}

fun uploadImageToFirebase(
    imageUri: Uri,
    storage: FirebaseStorage,
    database: DatabaseReference,
    name: String,
    price: String,
    soldBy: String,
    quantity: String,
    context: Context
) {
    val storageRef = storage.reference.child("images/${System.currentTimeMillis()}.jpg")
    val uploadTask = storageRef.putFile(imageUri)

    uploadTask.addOnSuccessListener { taskSnapshot ->
        // Get the image URL
        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            saveDataToRealtimeDatabase(
                database,
                name,
                price,
                soldBy,
                quantity,
                imageUrl,
                context
            )
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Image upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
    }
}

fun saveDataToRealtimeDatabase(
    database: DatabaseReference,
    name: String,
    price: String,
    soldBy: String,
    quantity: String,
    imageUrl: String,
    context: Context
) {
    val id = database.push().key ?: return
    val date = "12345"
    val username = "SampleUser" // Replace with actual username

    val product = Product(
        id = id,
        name = name,
        price = price,
        soldBy = soldBy,
        quantity = quantity,
        imageUrl = imageUrl,
        date = date,
        username = username
    )

    database.child(id).setValue(product).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to add product: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
        }
    }
}






@Composable
fun NavHostContainer(
    path:String,
    navController: NavHostController,
    padding: PaddingValues,
    authViewModel: AuthViewModel,
    mod:Modifier
) {

    NavHost(

        navController = navController,

        // set the start destination as home
        startDestination = "home",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home") {
                uploaddd(path,padding)
            }



            // route : profile
            composable("profile") {
                ProfileScreen(mod, authViewModel )
            }
        })

}

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),

        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)

@Composable
fun ProfileScreen(mod:Modifier,authViewModel: AuthViewModel) {
    // Column Composable,

        val authState by authViewModel.authState.observeAsState()
        val database = Firebase.database
        var userInfo by remember { mutableStateOf<user?>(null) }
        var isLoading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }


    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
        LaunchedEffect(userId) {
            if (userId != null) {
                val userRef = database.getReference("users").child(userId)
                userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(user::class.java)
                        userInfo = user
                        errorMessage = if (user == null) "User not found" else null
                        isLoading = false
                    }

                    override fun onCancelled(error: DatabaseError) {
                        errorMessage = error.message
                        isLoading = false
                    }
                })
            } else {
                errorMessage = "Invalid User ID"
                isLoading = false
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserInformationSection(userInfo, isLoading, errorMessage,mod)

            Text(
                text = "Order History",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 8.dp)
            )


        }
    }
