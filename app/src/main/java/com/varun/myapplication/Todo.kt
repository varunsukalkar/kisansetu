package com.varun.myapplication


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var roomid:Int=0,
    
    
    var id: String ,
    var name: String,
    var price: String,
    var soldBy: String,
    var quantity: String,
    var imageUrl: String,
    var date: String,
    var username: String)