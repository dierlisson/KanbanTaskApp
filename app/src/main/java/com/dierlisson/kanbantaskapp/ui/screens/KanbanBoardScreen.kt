package com.dierlisson.kanbantaskapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun KanbanBoardScreen(
    tasks: List<Task>,
    onAddTask: (String, String, String) -> Unit,
    onTaskClick: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    var showAddSheet by remember { mutableStateOf(false) }
    val columns = listOf("TODO" to "A Fazer", "DOING" to "Fazendo", "DONE" to "Concluído")

    val pagerState = rememberPagerState(pageCount = { columns.size })

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = { Text("KanbanTask", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F9FA))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true },
                containerColor = Color(0xFF4285F4),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 16.dp,
                // --- MUDANÇA AQUI: Alinhando o conteúdo ao topo ---
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp) // Adiciona um respiro acima das colunas
            ) { page ->
                val (status, title) = columns[page]

                KanbanColumn(
                    title = title,
                    tasks = tasks.filter { it.status == status },
                    onTaskClick = onTaskClick,
                    onDeleteTask = onDeleteTask
                )
            }

            // Indicadores de Página
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(columns.size) { iteration ->
                    val isCurrentPage = pagerState.currentPage == iteration
                    val color = if (isCurrentPage) Color(0xFF4285F4) else Color.LightGray
                    val size = if (isCurrentPage) 10.dp else 6.dp

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(size)
                    )
                }
            }
        }

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
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(max = 650.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE9ECEF))
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
            Badge(containerColor = Color.LightGray) {
                Text(text = tasks.size.toString(), color = Color.DarkGray)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            items(tasks) { task ->
                TaskCard(task, onTaskClick, onDeleteTask)
            }
        }
    }
}