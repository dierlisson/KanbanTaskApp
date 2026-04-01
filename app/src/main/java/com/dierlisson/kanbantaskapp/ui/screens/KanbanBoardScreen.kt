package com.dierlisson.kanbantaskapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dierlisson.kanbantaskapp.model.Task
import com.dierlisson.kanbantaskapp.ui.components.TaskCard

@Composable
fun KanbanBoardScreen(
    tasks: List<Task>,
    onAddTask: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    val columns = listOf("TODO" to "A Fazer", "DOING" to "Fazendo", "DONE" to "Concluído")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) { Text("+") }
        }
    ) { paddingValues ->
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(columns) { (status, title) ->
                KanbanColumn(
                    title = title,
                    tasks = tasks.filter { it.status == status },
                    onTaskClick = onTaskClick,
                    onDeleteTask = onDeleteTask
                )
            }
        }
    }
}

@Composable
fun KanbanColumn(
    title: String,
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    Column(modifier = Modifier.width(280.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(tasks) { task ->
                TaskCard(task, onTaskClick, onDeleteTask)
            }
        }
    }
}