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
                    // Instancia o ViewModel que gerencia a comunicação com a API
                    val viewModel: TaskViewModel = viewModel()

                    // Observa as mudanças na lista de tarefas em tempo real
                    val tasks by viewModel.tasks.collectAsState()

                    KanbanBoardScreen(
                        tasks = tasks,
                        onAddTask = { title, desc, status ->
                            // Chama a API para criar a tarefa
                            viewModel.addTask(title, desc, status)
                        },
                        onTaskClick = { task ->
                            // Regra de negócio: Clicar no card avança o status da tarefa
                            val nextStatus = when (task.status) {
                                "TODO" -> "DOING"
                                "DOING" -> "DONE"
                                else -> "DONE" // Se já está concluído, mantém
                            }
                            if (task.status != nextStatus) {
                                viewModel.updateTaskStatus(task, nextStatus)
                            }
                        },
                        onDeleteTask = { task ->
                            // Chama a API para deletar a tarefa
                            viewModel.deleteTask(task.id)
                        }
                    )
                }
            }
        }
    }
}