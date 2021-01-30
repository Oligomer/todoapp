package com.dpug.todoapp.fragments

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dpug.todoapp.R
import com.dpug.todoapp.data.models.Priority
import com.dpug.todoapp.data.models.Priority.*
import com.dpug.todoapp.data.models.ToDoData

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDatabaseIsEmpty(toDoData: List<ToDoData>) =
        emptyDatabase.postValue(toDoData.isEmpty())

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun equals(other: Any?) = super.equals(other)
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
        ) {
            when(position){
                0 -> (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))
                1 -> (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))
                2 -> (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))
            }
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean =
        title.isNotEmpty() && description.isNotEmpty()

    fun parsePriority(priority: String) = when(priority) {
        "Первоочередные дела" -> HIGH
        "Обычные дела" -> MEDIUM
        else -> LOW
    }

    fun parsePriorityToInt(priority: Priority) = when(priority) {
        HIGH -> 0
        MEDIUM -> 1
        LOW -> 2
    }
}