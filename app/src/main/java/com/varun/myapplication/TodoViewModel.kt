package com.varun.myapplication


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TodoViewModel : ViewModel() {

    val todoDao = MainActivity.todoDatabase.getTodoDao()


    val todoList : LiveData<List<Todo>> = todoDao.getAllTodo()

    fun addTodo(  id: String,
                  name: String,
                  price: String,
                  soldBy: String,
                  quantity: String,
                  imageUrl: String,
                  date: String,
                  username: String







                ){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(Todo(id=id,name=name, price = price, soldBy = soldBy, quantity = quantity, imageUrl = imageUrl, date = date, username = username))
        }
    }

    fun deleteTodo(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }

    fun deleteevery(){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.nukeTable()
        }
    }
    // Upload all Todo items from Room to Firebase under the "history" node

    }

