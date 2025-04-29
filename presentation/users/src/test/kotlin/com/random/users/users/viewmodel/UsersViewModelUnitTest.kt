package com.random.users.users.viewmodel

import app.cash.turbine.test
import arrow.core.left
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.test.rules.MainDispatcherRule
import com.random.users.users.contract.UsersErrorUiState
import com.random.users.users.contract.UsersUiEvent
import com.random.users.users.contract.UsersScreenUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.getValue
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class UsersViewModelUnitTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersListUseCase: GetUserListUseCase = mockk()
    private val deleteUserUseCase: DeleteUserUseCase = mockk()
    private val viewModel: UsersViewModel by lazy {
        UsersViewModel(getUsersListUseCase, deleteUserUseCase)
    }

    @Test
    fun `GIVEN deleteUser returns error WHEN deleteUserUseCase THEN receives error state`() =
        runTest {
            coEvery { deleteUserUseCase("1") } returns UsersErrors.UserError.left()

            viewModel.handleEvent(UsersUiEvent.OnDeleteUser("1"))
            runCurrent()

            viewModel.uiState.test {
                assertTrue(awaitItem().contentState is UsersScreenUiState.ContentState.Idle)
                expectNoEvents()
            }

            viewModel.uiEventsState.test {
                assertEquals(UsersErrorUiState.DeleteError, awaitItem())
                expectNoEvents()
            }

            coVerify {
                deleteUserUseCase("1")
            }
        }
}
