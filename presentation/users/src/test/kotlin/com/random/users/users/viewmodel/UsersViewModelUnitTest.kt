package com.random.users.users.viewmodel

import app.cash.turbine.test
import arrow.core.left
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.test.rules.MainDispatcherRule
import com.random.users.users.contract.UsersErrorUiEventsState
import com.random.users.users.contract.UsersEvent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.getValue
import kotlin.test.assertEquals

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
