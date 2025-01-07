package com.varun.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database

//class AuthViewModel : ViewModel() {
//
//    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
//
//
//    val database = Firebase.database
//    val myRef = database.getReference("user")
//
//
//    private val _authState = MutableLiveData<AuthState>()
//    val authState: LiveData<AuthState> = _authState
//
//    init {
//        checkAuthStatus()
//    }
//
//
//    fun checkAuthStatus(){
//        if(auth.currentUser==null){
//            _authState.value = AuthState.Unauthenticated
//        }else{
//            _authState.value = AuthState.Authenticated
//        }
//    }
//
//    fun login(email : String,password : String){
//
//        if(email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthState.Error("Email or password can't be empty")
//            return
//        }
//        _authState.value = AuthState.Loading
//        auth.signInWithEmailAndPassword(email,password)
//            .addOnCompleteListener{task->
//                if (task.isSuccessful){
//                    _authState.value = AuthState.Authenticated
//                }else{
//                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
//                }
//            }
//    }
//
//    fun signup(email : String,password : String,name:String,role:String,mobileno:String){
//
//        if(email.isEmpty() || password.isEmpty()|| name.isEmpty()||role.isEmpty()||mobileno.isEmpty()){
//            _authState.value = AuthState.Error("Email or password can't be empty")
//            return
//        }
//        _authState.value = AuthState.Loading
//        auth.createUserWithEmailAndPassword(email,password)
//            .addOnCompleteListener{task->
//                if (task.isSuccessful){
//                    val sinfo=user(email,password,name,role,mobileno)
//                    myRef.child(name).setValue(sinfo).addOnSuccessListener {
//
//
//                        _authState.value = AuthState.Authenticated
//                    }.addOnFailureListener{
//                        _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
//                    }
//                }else{
//                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
//                }
//            }
//    }
//
//    fun signout(){
//        auth.signOut()
//        _authState.value = AuthState.Unauthenticated
//    }
//
//
//}
//
//
//sealed class AuthState{
//    object Authenticated : AuthState()
//    object Unauthenticated : AuthState()
//    object Loading : AuthState()
//    data class Error(val message : String) : AuthState()
//}



//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase

//class AuthViewModel : ViewModel() {
//
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val database = Firebase.database
//    private val userRef = database.getReference("users")
//
//    // LiveData for authentication state
//    private val _authState = MutableLiveData<AuthState>()
//    val authState: LiveData<AuthState> get() = _authState
//
//    init {
//        // Check authentication status on initialization
//        checkAuthStatus()
//    }
//
//    /**
//     * Check if the user is authenticated.
//     */
//    fun checkAuthStatus() {
//        _authState.value = if (auth.currentUser != null) {
//            AuthState.Authenticated(auth.currentUser?.uid.orEmpty())
//        } else {
//            AuthState.Unauthenticated
//        }
//    }
//
//    /**
//     * Perform login with email and password.
//     */
//    fun login(email: String, password: String) {
//        if (email.isBlank() || password.isBlank()) {
//            _authState.value = AuthState.Error("Email or password cannot be empty")
//            return
//        }
//
//        _authState.value = AuthState.Loading
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _authState.value = AuthState.Authenticated(auth.currentUser?.uid.orEmpty())
//                } else {
//                    _authState.value =
//                        AuthState.Error(task.exception?.localizedMessage ?: "Login failed")
//                }
//            }
//    }
//
//    /**
//     * Perform sign-up and store user details in the Realtime Database.
//     */
//    fun signup(email: String, password: String, name: String, role: String, mobileNo: String) {
//        if (email.isBlank() || password.isBlank() || name.isBlank() || role.isBlank() || mobileNo.isBlank()) {
//            _authState.value = AuthState.Error("All fields must be filled")
//            return
//        }
//
//        _authState.value = AuthState.Loading
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val userId = auth.currentUser?.uid
//                    if (userId != null) {
//                        val userInfo = user(email,password, name, role, mobileNo)
//                        userRef.child(userId).setValue(userInfo)
//                            .addOnSuccessListener {
//                                _authState.value = AuthState.Authenticated(userId)
//                            }
//                            .addOnFailureListener {
//                                _authState.value = AuthState.Error("Failed to save user data")
//                            }
//                    } else {
//                        _authState.value = AuthState.Error("Failed to retrieve user ID")
//                    }
//                } else {
//                    _authState.value =
//                        AuthState.Error(task.exception?.localizedMessage ?: "Sign-up failed")
//                }
//            }
//    }
//
//    /**
//     * Log out the current user.
//     */
//    fun logout() {
//        auth.signOut()
//        _authState.value = AuthState.Unauthenticated
//    }
//
//    private fun fetchUserRole(userId: String) {
//        _authState.value = AuthState.Loading
//        userRef.child(userId).get()
//            .addOnSuccessListener { snapshot ->
//                val role = snapshot.child("role").getValue(String::class.java)
//                if (role != null) {
//                    if (role == "Farmer") {
//                        _authState.value = AuthState.NavigateToUploadFarmer
//                    } else {
//                        _authState.value = AuthState.NavigateToHome
//                    }
//                } else {
//                    _authState.value = AuthState.Error("Role not found")
//                }
//            }
//            .addOnFailureListener {
//                _authState.value = AuthState.Error("Failed to fetch role: ${it.message}")
//            }
//    }
//    /**
//     * Get the currently authenticated user's UID.
//     */
//    fun getUserId(): String? {
//        return auth.currentUser?.uid
//    }
//}


/**
 * Sealed class to represent authentication states.
 */
//sealed class AuthState {
//    data class Authenticated(val userId: String) : AuthState()
//    object Unauthenticated : AuthState()
//    object Loading : AuthState()
//    data class Error(val message: String) : AuthState()
//}

//sealed class AuthState {
//    data class Authenticated(val userId: String) : AuthState() // Store user ID or any other necessary info
//    object Unauthenticated : AuthState()
//    object Loading : AuthState()
//    object NavigateToHome : AuthState()
//    object NavigateToUploadFarmer : AuthState()
//    data class Error(val message: String) : AuthState()
//}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database
    private val userRef = database.getReference("users")

    // LiveData for authentication state
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    init {
        checkAuthStatus()
    }

    /**
     * Check if the user is authenticated.
     */
    fun checkAuthStatus() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchUserRole(currentUser.uid)
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    /**
     * Fetch the user's role from the database.
     */
//    fun fetchUserRole(userId: String) {
//        _authState.value = AuthState.Loading
//        userRef.child(userId).get()
//            .addOnSuccessListener { snapshot ->
//                val role = snapshot.child("role").getValue(String::class.java)
//                if (role != null) {
//                    if (role == "Farmer") {
//                        _authState.value = AuthState.NavigateToUploadFarmer
//                    } else {
//                        _authState.value = AuthState.NavigateToHome
//                    }
//                } else {
//                    _authState.value = AuthState.Error("Role not found")
//                }
//            }
//            .addOnFailureListener {
//                _authState.value = AuthState.Error("Failed to fetch role: ${it.message}")
//            }
//    }


    fun fetchUserRole(userId: String) {
        userRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val role = snapshot.child("role").getValue(String::class.java) ?: ""
                if (role == "Farmer") {
                    _authState.value = AuthState.Role("Farmer") // Set role as Farmer
                } else {
                    _authState.value = AuthState.Role("NonFarmer") // Set role as NonFarmer
                }
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error("Failed to fetch role")
            }
    }











    fun signup(email: String, password: String, name: String, role: String, mobileNo: String) {
        if (email.isBlank() || password.isBlank() || name.isBlank() || role.isBlank() || mobileNo.isBlank()) {
            _authState.value = AuthState.Error("All fields must be filled")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val userInfo = user(email,password, name, role, mobileNo)
                        userRef.child(userId).setValue(userInfo)
                            .addOnSuccessListener {
                                if (role == "Farmer") {
                                    _authState.value = AuthState.Role("Farmer")
                                } else {
                                    _authState.value = AuthState.Role("NonFarmer")
                                }

                            }
                            .addOnFailureListener {
                                _authState.value = AuthState.Error("Failed to save user data")
                            }
                    } else {
                        _authState.value = AuthState.Error("Failed to retrieve user ID")
                    }
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.localizedMessage ?: "Sign-up failed")
                }
            }
    }
    /**
     * Perform login with email and password.
     */
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        fetchUserRole(userId) // Fetch role after successful login
                    }
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.localizedMessage ?: "Login failed")
                }
            }
    }

    /**
     * Log out the current user.
     */
    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    /**
     * Get the currently authenticated user's UID.
     */
    fun getUserId(): String? {
        return auth.currentUser?.uid
    }
}

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Authenticated(val userId: String) : AuthState()
    data class Error(val message: String) : AuthState()
    data class Role(val role: String) : AuthState()  // We use this for roles like "Farmer" or "NonFarmer"
}