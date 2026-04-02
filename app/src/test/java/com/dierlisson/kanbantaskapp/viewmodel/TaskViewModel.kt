package com.dierlisson.kanbantaskapp.viewmodel

import com.dierlisson.kanbantaskapp.api.RetrofitClient
import com.dierlisson.kanbantaskapp.api.TaskApiService
import com.dierlisson.kanbantaskapp.model.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockApi: TaskApiService
    private lateinit var viewModel: TaskViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock da API para não fazer requisições reais durante o teste
        mockApi = mockk()
        mockkObject(RetrofitClient)
        coEvery { RetrofitClient.apiService } returns mockApi
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `ao inicializar, deve buscar tarefas da API e atualizar o estado`() = runTest {
        // Arrange
        val mockTasks = listOf(
            Task("1", "Estudar Jetpack Compose", "Refazer UI", "TODO"),
            Task("2", "Configurar Retrofit", "Conectar MockAPI", "DONE")
        )
        coEvery { mockApi.getTasks() } returns mockTasks

        // Act
        viewModel = TaskViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertEquals(mockTasks, viewModel.tasks.value)
        coVerify(exactly = 1) { mockApi.getTasks() }
    }

    @Test
    fun `ao adicionar tarefa, deve chamar a API e recarregar a lista`() = runTest {
        // Arrange
        val newTask = Task(title = "Nova Task", description = "Desc", status = "TODO")
        coEvery { mockApi.getTasks() } returns emptyList() // Retorno inicial
        coEvery { mockApi.createTask(any()) } returns newTask // Mock da criação

        viewModel = TaskViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        // Act
        viewModel.addTask("Nova Task", "Desc", "TODO")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) { mockApi.createTask(any()) }
        coVerify(exactly = 2) { mockApi.getTasks() } // Uma no init, outra após o add
    }

    @Test
    fun `ao atualizar tarefa, deve chamar a API e recarregar a lista`() = runTest {
        // Arrange
        val existingTask = Task("1", "Tarefa Antiga", "Desc", "TODO")
        val updatedTask = existingTask.copy(title = "Tarefa Nova", description = "Nova Desc", status = "DOING")

        coEvery { mockApi.getTasks() } returns emptyList()
        coEvery { mockApi.updateTask(any(), any()) } returns updatedTask

        viewModel = TaskViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        // Act
        viewModel.updateTaskDetails(existingTask, "Tarefa Nova", "Nova Desc", "DOING")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        // Verifica se a API de update foi chamada com os dados corretos
        coVerify(exactly = 1) { mockApi.updateTask("1", updatedTask) }
        // Verifica se a lista foi recarregada
        coVerify(exactly = 2) { mockApi.getTasks() }
    }
}