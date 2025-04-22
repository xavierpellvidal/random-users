package com.random.users.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.test.model.getUserListResponsePage1Json
import com.random.users.test.rules.MainDispatcherRule
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coVerify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import kotlin.getValue
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
internal class UsersViewModelIntegrationTest {
    @Rule(order = 0)
    @JvmField
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var instantRule: TestRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    var mainRule: TestRule = MainDispatcherRule()

    @Inject
    lateinit var getUsersListUseCase: GetUserListUseCase

    @Inject
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @Inject
    lateinit var mockWebServer: MockWebServer

    lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = UsersViewModel(getUsersListUseCase, deleteUserUseCase)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN load users event THEN receives correct state`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

            viewModel.handleEvent(UsersEvent.OnLoadUsers)
            advanceUntilIdle()

            viewModel.uiState.test {
                val initialState = awaitItem()
                val loadingState = awaitItem()
                assertTrue(initialState.contentState is UsersScreenUiState.ContentState.Loading)
                assertTrue(loadingState.contentState is UsersScreenUiState.ContentState.Idle)
                expectNoEvents()
            }
        }
}
