package com.dierlisson.kanbantaskapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.dierlisson.kanbantaskapp.model.Task
import com.dierlisson.kanbantaskapp.ui.screens.KanbanBoardScreen
import org.junit.Rule
import org.junit.Test

class KanbanBoardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun kanbanBoard_exibePrimeiraColunaEFloatingActionButton() {
        val testTasks = listOf(
            Task("1", "Tarefa de Teste", "Descrição da tarefa", "TODO")
        )

        composeTestRule.setContent {
            KanbanBoardScreen(
                tasks = testTasks,
                onAddTask = { _, _, _ -> },
                onEditTask = { _, _, _, _ -> }, // Adicionado parâmetro de edição
                onTaskClick = {},
                onDeleteTask = {}
            )
        }

        // Verifica se o título da primeira coluna do Pager está visível
        composeTestRule.onNodeWithText("A Fazer").assertIsDisplayed()

        // Verifica se a tarefa injetada na lista está sendo renderizada no card
        composeTestRule.onNodeWithText("Tarefa de Teste").assertIsDisplayed()
        composeTestRule.onNodeWithText("Descrição da tarefa").assertIsDisplayed()

        // Verifica se o botão flutuante de adicionar (+) está na tela
        composeTestRule.onNodeWithContentDescription("Adicionar Tarefa").assertIsDisplayed()
    }

    @Test
    fun kanbanBoard_abreBottomSheetAoClicarNoFab() {
        composeTestRule.setContent {
            KanbanBoardScreen(
                tasks = emptyList(),
                onAddTask = { _, _, _ -> },
                onEditTask = { _, _, _, _ -> },
                onTaskClick = {},
                onDeleteTask = {}
            )
        }

        // Verifica que o Bottom Sheet NÃO está visível inicialmente
        composeTestRule.onNodeWithText("Nova Tarefa").assertDoesNotExist()

        // Clica no Floating Action Button
        composeTestRule.onNodeWithContentDescription("Adicionar Tarefa").performClick()

        // Verifica se o Bottom Sheet subiu e os elementos do formulário estão visíveis
        composeTestRule.onNodeWithText("Nova Tarefa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Título").assertIsDisplayed()
        composeTestRule.onNodeWithText("Descrição").assertIsDisplayed()
        composeTestRule.onNodeWithText("Salvar Tarefa").assertIsDisplayed()
    }

    @Test
    fun kanbanBoard_abreBottomSheetParaEdicaoAoClicarNoIcone() {
        val testTasks = listOf(
            Task("1", "Tarefa a Editar", "Descrição Antiga", "TODO")
        )

        composeTestRule.setContent {
            KanbanBoardScreen(
                tasks = testTasks,
                onAddTask = { _, _, _ -> },
                onEditTask = { _, _, _, _ -> },
                onTaskClick = {},
                onDeleteTask = {}
            )
        }

        // Clica no ícone de edição da tarefa
        composeTestRule.onNodeWithContentDescription("Editar Tarefa").performClick()

        // Verifica se o formulário mudou para o modo de edição
        composeTestRule.onNodeWithText("Editar Tarefa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Atualizar Tarefa").assertIsDisplayed()

        // Como o card continua no fundo e o form abriu por cima, esperamos encontrar 2 elementos com o mesmo texto
        composeTestRule.onAllNodesWithText("Tarefa a Editar").assertCountEquals(2)
        composeTestRule.onAllNodesWithText("Descrição Antiga").assertCountEquals(2)
    }
}