package com.varun.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo ")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo : Todo)

    @Query("Delete FROM Todo where roomid = :id")
    fun deleteTodo(id : Int)


        @Query("DELETE FROM Todo")
        fun nukeTable()

    @Query("SELECT * FROM Todo") // Adjust your table name
    fun getTodos(): LiveData<List<Todo>>



}