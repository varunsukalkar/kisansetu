package com.varun.myapplication


import android.os.Parcel
import android.os.Parcelable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


//
//
//data class MyDataClass(
//    val modifier: Modifier? = null,
//    val authViewModel: AuthViewModel,
//    val someOtherData: String
//)
import java.io.Serializable

data class MyDataClass(
       val navigation:NavController,
    val authViewModel: AuthViewModel,

) : Serializable