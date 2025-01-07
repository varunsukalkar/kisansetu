package com.varun.myapplication


import android.content.Intent
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation
import kotlinx.coroutines.delay
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun HomePage( navController: NavController, authViewModel: AuthViewModel) {

Modifier.background(Color.White)
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")

                else-> Unit
        }

    }

//    val authState by authViewModel.authState.observeAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(authState) {
//        when (authState) {
//            is AuthState.Authenticated -> {
//                val userId = (authState as AuthState.Authenticated).userId
//                authViewModel.fetchUserRole(
//                    userId = userId,
//                    onRoleFetched = { role ->
//                        if (role == "Farmer") {
//                            navController.navigate("uploadfarmer")
//                        } else {
//                            navController.navigate("hhh")
//                        }
//                    },
//                    onError = { error ->
//                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                    }
//                )
//            }
//            is AuthState.Unauthenticated -> {
//                navController.navigate("login")
//            }
//            else -> Unit
//        }
//    }

 ScrollableTopBar(authViewModel)
 //  FormScreen()
   // symbol()
}


@Composable
fun DisplayUserInfo(authViewModel: AuthViewModel,mod: Modifier = Modifier) {
    val authState by authViewModel.authState.observeAsState()
    val database = Firebase.database
    var userInfo by remember { mutableStateOf<user?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

   // val userId = (authState as? AuthState.Authenticated)?.userId
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

        DisplayOrders()
    }
}

@Composable
fun UserInformationSection(userInfo: user?, isLoading: Boolean, errorMessage: String?,mod: Modifier = Modifier) {
    Box(
        modifier = mod
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            userInfo != null -> Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "User Information", style = MaterialTheme.typography.titleMedium)
                Text(text = "Name: ${userInfo.name}", style = MaterialTheme.typography.titleSmall)
                Text(text = "Email: ${userInfo.email}", style = MaterialTheme.typography.titleSmall)
                Text(text = "Role: ${userInfo.role}", style = MaterialTheme.typography.titleSmall)
                Text(text = "Mobile No: ${userInfo.mobileNo}", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun DisplayOrders() {
    val orders = remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        if (userId != null) {
            val database = Firebase.database
            val orderRef = database.getReference("users").child(userId).child("orderHistory")
            orderRef.get().addOnSuccessListener { snapshot ->
                orders.value = snapshot.children.mapNotNull { it.getValue(Order::class.java) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(orders.value) { order ->
            OrderItem(order)
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Amount: ${order.amount}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Date: ${order.date}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Time: ${order.time}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Day: ${order.dayOfWeek}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Location: ${order.location}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ScreenContent(selectedIndex: Int, mod: Modifier = Modifier,authViewModel: AuthViewModel) {
    val todoViewModel:TodoViewModel= viewModel(LocalContext.current as ViewModelStoreOwner)
    when (selectedIndex) {
        0 -> homevala( mod)
        1-> bk(mod)
         2->  DisplayUserInfo(authViewModel,mod)
        3-> TodoListPage(todoViewModel,mod)





    }
}



@Composable
fun bk(mod: Modifier = Modifier){
    val context = LocalContext.current
    val scrollState= rememberScrollState()
    Column(
        modifier = mod.background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Banneritem2( banner(R.drawable.q4,1), onDelete = {
            context.startActivity(Intent(context,MainActivity2::class.java))
        },"Vegetable")
        Spacer(modifier = Modifier.height(5.dp))
        Banneritem2( banner(R.drawable.q3,1), onDelete = {
            context.startActivity(Intent(context,MainActivity4::class.java))
        },"Fruits")
        Spacer(modifier = Modifier.height(5.dp))
        Banneritem2( banner(R.drawable.img2,1), onDelete = {
            context.startActivity(Intent(context,MainActivity5::class.java))
        },"Hearbs")
    }



}


@Composable
fun BottomNavigationBar(buttons: List<RioBottomNavItemData>) {
    RioBottomNavigation(
        fabIcon = ImageVector.vectorResource(id = R.drawable.ic_qr),

        buttons = buttons,
        fabSize = 50.dp,
        barHeight = 60.dp,


      // selectedItemColor = fabColor,
        //fabBackgroundColor = fabColor,

    )
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableTopBar(authViewModel: AuthViewModel,) {
    val context = LocalContext.current
    val locationHelper = LocationHelper(context)
    val addressState = remember { mutableStateOf("Select your address") }

    // Fetch the address asynchronously
    LaunchedEffect(Unit) {
        locationHelper.getAddressString { address ->
            addressState.value = address ?: "Select your address"
        }
    }
    val items = listOf(
        R.drawable.ic_explore,
        R.drawable.ic_payment,
        R.drawable.ic_transfer,
        R.drawable.baseline_shopping_cart_24

    )

    val labels = listOf(
        "Home",
        "categories",
        "Transfer",
        "Cart",

    )

    // Use rememberSaveable to retain state across configuration changes
    var selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    // Create RioBottomNavItemData for the bottom navigation buttons
    val buttons = items.mapIndexed { index, iconData ->
        RioBottomNavItemData(
            imageVector = ImageVector.vectorResource(iconData),
            selected = index == selectedIndex.intValue,
            onClick = { selectedIndex.intValue = index },
            label = labels[index]
        )
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
val scrollState= rememberScrollState()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(0.dp, 0.dp, 0.dp, 40.dp),
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
            BottomNavigationBar(buttons = buttons)
        },
        content = { innerPadding ->
            ScreenContent(selectedIndex.intValue, mod = Modifier.padding(innerPadding),authViewModel)


        }
    )
}

@Composable
fun  banner(){
    var bannerlist  = listOf<banner>(
         banner(R.drawable.q4,1),
        banner(R.drawable.q3,1),
        banner(R.drawable.q2,1),
        banner(R.drawable.q1,1),

    )





    val scrollState = rememberLazyListState()
    var scrollDirection by remember { mutableStateOf(1) } // 1 = to right, -1 = to left
    val context = LocalContext.current

    LazyRow (
        state = scrollState,

        horizontalArrangement = Arrangement.spacedBy(4.dp),

        content = {
            itemsIndexed(bannerlist, itemContent = {index,items->


                Banneritem(items, onDelete = {

                    context.startActivity(Intent(context,MainActivity4::class.java))
                })

            })
        }
    )



    LaunchedEffect(Unit) {
        delay(2000)
        val needToScrollItems = bannerlist.size - scrollState.layoutInfo.visibleItemsInfo.size + 1
        val scrollWidth = needToScrollItems * (350 + 4)
        while (true) {
            scrollState.animateScrollBy(
                value = (scrollDirection * scrollWidth).toFloat(),
                animationSpec = tween(
                    durationMillis = 4000 * needToScrollItems,
                    easing = LinearEasing
                )
            )
            scrollDirection *= -1
        }
    }

}

@Composable
fun Banneritem(item : banner,onDelete : ()-> Unit) {
    Card( onClick = onDelete,
        modifier = Modifier
            .size(350.dp, 150.dp)
            .padding(10.dp, 10.dp, 10.dp, 10.dp)
            .background(Color.White)


    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = "this is a banner",
            contentScale = ContentScale.Crop,

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .fillMaxSize()



        )
    }
}


@Composable
fun Banneritem2(
    item: banner,
    onDelete: () -> Unit,
    text: String // Pass text as a string argument
) {
    Card(

        onClick = onDelete,
        modifier = Modifier
            .size(350.dp, 220.dp)
            .padding(10.dp)
            .background(Color.White), // Ensure the card has a white background
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set the Card's container color to white
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image at the top
            Image(
                painter = painterResource(item.image),
                contentDescription = "this is a banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(350.dp,150.dp)
                    .padding(10.dp)
            )

            // Text below the image
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center, // Center align the text
                style = MaterialTheme.typography.bodyMedium // Use Material theme typography
            )
        }
    }
}
