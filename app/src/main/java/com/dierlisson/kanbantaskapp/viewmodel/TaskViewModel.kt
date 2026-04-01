package com.dierlisson.kanbantaskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dierlisson.kanbantaskapp.api.RetrofitClient
import com.dierlisson.kanbantaskapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val api = RetrofitClient.apiService

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            try {
                val response = api.getTasks()
                println("API_DEBUG: Tarefas recebidas: ${response.size}")

                if (response.isNotEmpty()) {
                    println("API_DEBUG: Conteúdo da primeira tarefa: ${response[0]}")
                }

                _tasks.value = response
            } catch (e: Exception) {
                println("API_DEBUG: Erro ao buscar: ${e.message}")
                e.printStackTrace()
            }
        }
    }
    fun addTask(title: String, description: String, status: String = "TODO") {
        viewModelScope.launch {
            try {
                val newTask = Task(title = title, description = description, status = status)
                api.createTask(newTask)
                fetchTasks() // Recarrega a lista após adicionar
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTaskStatus(task: Task, newStatus: String) {
        viewModelScope.launch {
            try {
                val updatedTask = task.copy(status = newStatus)
                api.updateTask(task.id, updatedTask)
                fetchTasks()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            try {
                api.deleteTask(taskId)
                fetchTasks()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}