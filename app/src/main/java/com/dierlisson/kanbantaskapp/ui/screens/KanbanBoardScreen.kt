package com.dierlisson.kanbantaskapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dierlisson.kanbantaskapp.model.Task
import com.dierlisson.kanbantaskapp.ui.components.AddTaskSheet
import com.dierlisson.kanbantaskapp.ui.components.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanbanBoardScreen(
    tasks: List<Task>,
    onAddTask: (String, String, String) -> Unit,
    onTaskClick: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    var showAddSheet by remember { mutableStateOf(false) }
    val columns = listOf("TODO" to "A Fazer", "DOING" to "Fazendo", "DONE" to "Concluído")

    Scaffold(
        containerColor = Color(0xFFF8F9FA), // Cor de fundo clara do app
        topBar = {
            TopAppBar(
                title = { Text("KanbanTask", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F9FA))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true },
                containerColor = Color(0xFF4285F4), // Azul do mockup
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { paddingValues ->

        // LazyRow garante o scroll horizontal para os lados
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
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

        // Exibe o BottomSheet se a variável de estado for true
        if (showAddSheet) {
            AddTaskSheet(
                onDismiss = { showAddSheet = false },
                onSaveTask = { title, desc, status ->
                    onAddTask(title, desc, status)
                }
            )
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
    Column(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE9ECEF)) // Fundo cinza claro da coluna
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF495057)
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Badge com a quantidade de tarefas na coluna
            Badge(containerColor = Color.LightGray) {
                Text(text = tasks.size.toString(), color = Color.DarkGray)
            }
        }

        // LazyColumn para scroll vertical DENTRO da coluna
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(tasks) { task ->
                TaskCard(task, onTaskClick, onDeleteTask)
            }
        }
    }
}