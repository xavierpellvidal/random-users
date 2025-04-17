package com.random.users.users.viewmodel

import app.cash.turbine.test
import arrow.core.right
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import com.random.users.users.mapper.toUiState
import com.random.users.users.mother.UsersMother
import com.random.users.users.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.getValue
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UsersViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersListUseCase: GetUserListUseCase = mockk()
    private val deleteUserUseCase: DeleteUserUseCase = mockk()
    private val viewModel: UsersViewModel by lazy {
        UsersViewModel(getUsersListUseCase, deleteUserUseCase)
    }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN load users event THEN receives correct state`() =
        runTest {
            val expectedUsers = UsersMother.createList(20)
            coEvery { getUsersListUseCase(any()) } returns expectedUsers.right()

            viewModel.handleEvent(UsersEvent.OnLoadUsers)

            viewModel.uiState.test {
                assertTrue(awaitItem().contentState is UsersScreenUiState.ContentState.Loading)
                assertEquals(expectedUsers.toUiState(), awaitItem().users)
                expectNoEvents()
            }

            coVerify {
                getUsersListUseCase(any())
            }
            coVerify(exactly = 0) { deleteUserUseCase(any()) }
        }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN filter users event with text THEN receives correct state`() =
        runTest {
            val expectedUsers = UsersMother.createList(20)
            coEvery { getUsersListUseCase(any()) } returns expectedUsers.right()

            viewModel.handleEvent(UsersEvent.OnFilterUsers("test"))

            viewModel.uiState.test {
                val item = awaitItem()
                assertTrue(item.contentState is UsersScreenUiState.ContentState.Filtered)
                assertEquals(item.filterText, "test")
            }

            coVerify(exactly = 0) {
                getUsersListUseCase(any())
                deleteUserUseCase(any())
            }
        }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN filter users event with no text THEN receives correct state`() =
        runTest {
            val expectedUsers = UsersMother.createList(20)
            coEvery { getUsersListUseCase(any()) } returns expectedUsers.right()

            viewModel.handleEvent(UsersEvent.OnFilterUsers(""))

            viewModel.uiState.test {
                val item = awaitItem()
                assertTrue(item.contentState is UsersScreenUiState.ContentState.Idle)
                assertEquals(item.filterText, "")
            }

            coVerify(exactly = 0) {
                getUsersListUseCase(any())
                deleteUserUseCase(any())
            }
        }
}
