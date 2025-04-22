package com.random.users.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.test.model.getUserListResponsePage1Json
import com.random.users.test.rules.MainDispatcherRule
import com.random.users.users.contract.UsersErrorUiEventsState
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
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
import kotlin.test.assertEquals
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
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun initViewModel() {
        viewModel = UsersViewModel(getUsersListUseCase, deleteUserUseCase)
    }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN load users event THEN receives Idle state`() =
        runTest {
            initViewModel()
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

            viewModel.handleEvent(UsersEvent.OnLoadUsers)
            runCurrent()

            viewModel.uiState.test {
                val initialState = awaitItem()
                val finalState = awaitItem()
                assertTrue(initialState.contentState is UsersScreenUiState.ContentState.Loading)
                assertTrue(finalState.contentState is UsersScreenUiState.ContentState.Idle)
                assertTrue(finalState.users.isNotEmpty())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN getUsersListUseCase returns error WHEN load users event THEN receives Error state`() =
        runTest {
            initViewModel()
            mockWebServer.enqueue(MockResponse().setResponseCode(500))

            viewModel.handleEvent(UsersEvent.OnLoadUsers)
            runCurrent()

            viewModel.uiState.test {
                val initialState = awaitItem()
                val finalState = awaitItem()
                assertTrue(initialState.contentState is UsersScreenUiState.ContentState.Loading)
                assertTrue(finalState.contentState is UsersScreenUiState.ContentState.Error)
                expectNoEvents()
            }

            viewModel.uiEventsState.test {
                assertEquals(UsersErrorUiEventsState.LoadUsersError, awaitItem())
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN filter users event with text THEN receives Filtered state`() =
        runTest {
            initViewModel()
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

            viewModel.handleEvent(UsersEvent.OnLoadUsers)
            runCurrent()

            viewModel.uiState.test {
                skipItems(2)
                viewModel.handleEvent(UsersEvent.OnFilterUsers("Jos"))
                runCurrent()

                val newState = awaitItem()
                assertTrue(newState.users.size == 1)
                assertTrue(newState.contentState is UsersScreenUiState.ContentState.Filtered)
                assertEquals(newState.filterText, "Jos")
                expectNoEvents()
            }
        }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN filter users event with no text THEN receives correct state`() =
        runTest {
            initViewModel()
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

            viewModel.handleEvent(UsersEvent.OnFilterUsers(""))
            runCurrent()

            viewModel.uiState.test {
                val newState = awaitItem()
                assertTrue(newState.contentState is UsersScreenUiState.ContentState.Idle)
                assertEquals(newState.filterText, "")
                expectNoEvents()
            }
        }
}
