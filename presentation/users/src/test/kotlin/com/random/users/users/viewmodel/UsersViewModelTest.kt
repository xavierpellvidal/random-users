package com.random.users.users.viewmodel

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.users.contract.UsersErrorUiEventsState
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import com.random.users.users.mapper.toUiState
import com.random.users.users.mother.UserMother
import com.random.users.users.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN load users event THEN receives correct state`() =
        runTest {
            val expectedUsers =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
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
    fun `GIVEN getUsersListUseCase returns error WHEN load users event THEN receives error state`() =
        runTest {
            coEvery { getUsersListUseCase(any()) } returns UsersErrors.NetworkError.left()

            viewModel.uiEventsState.test {
                viewModel.handleEvent(UsersEvent.OnLoadUsers)
                assertEquals(UsersErrorUiEventsState.LoadUsersError, awaitItem())
                expectNoEvents()
            }

            coVerify {
                getUsersListUseCase(any())
            }
        }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN filter users event with text THEN receives correct state`() =
        runTest {
            val expectedUsers =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
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
            val expectedUsers =
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                )
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

    @Test
    fun `GIVEN deleteUser returns error WHEN deleteUserUseCase THEN receives error state`() =
        runTest {
            coEvery { deleteUserUseCase("1") } returns UsersErrors.UserError.left()

            viewModel.uiEventsState.test {
                viewModel.handleEvent(UsersEvent.OnDeleteUser("1"))
                assertEquals(UsersErrorUiEventsState.DeleteError, awaitItem())
                expectNoEvents()
            }

            coVerify {
                deleteUserUseCase(any())
            }
        }
}
