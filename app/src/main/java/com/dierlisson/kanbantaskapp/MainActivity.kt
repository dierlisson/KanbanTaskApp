package com.dierlisson.kanbantaskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dierlisson.kanbantaskapp.ui.screens.KanbanBoardScreen
import com.dierlisson.kanbantaskapp.ui.theme.KanbanTaskAppTheme
import com.dierlisson.kanbantaskapp.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanbanTaskAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: TaskViewModel = viewModel()
                    val tasks by viewModel.tasks.collectAsState()

                    KanbanBoardScreen(
                        tasks = tasks,
                        onAddTask = { title, desc, status ->
                            viewModel.addTask(title, desc, status)
                        },
                        onTaskClick = { task ->

                            val nextStatus = when (task.status) {
                                "TODO" -> "DOING"
                                "DOING" -> "DONE"
                                else -> "TODO"
                            }
                            viewModel.updateTaskStatus(task, nextStatus)
                        },
                        onDeleteTask = { task ->
                            viewModel.deleteTask(task.id)
                        }
                    )
                }
            }
        }
    }
}