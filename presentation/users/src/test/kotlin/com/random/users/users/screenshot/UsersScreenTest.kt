
package com.random.users.users.screenshot

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.ext.junit.runners.AndroidJUnit4
import arrow.core.right
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.random.users.domain.models.UserName
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.test.rules.createRoborazziRule
import com.random.users.test.rules.createScreenshotTestComposeRule
import com.random.users.users.mother.UserMother
import com.random.users.users.screen.UsersScreen
import com.random.users.users.viewmodel.UsersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4a)
internal class UsersScreenTest {
    private val getUsersListUseCase: GetUserListUseCase = mockk()
    private val deleteUserUseCase: DeleteUserUseCase = mockk()
    private val viewModel: UsersViewModel by lazy {
        UsersViewModel(getUsersListUseCase, deleteUserUseCase)
    }

    @get:Rule
    val composeTestRule = createScreenshotTestComposeRule()

    @get:Rule
    val roborazziRule =
        createRoborazziRule(composeTestRule = composeTestRule, captureType = RoborazziRule.CaptureType.None)

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN load screen THEN idle state with data`() =
        runTest {
            coEvery { getUsersListUseCase(any()) } returns
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2"),
                    UserMother.createModel(uuid = "3"),
                    UserMother.createModel(uuid = "4"),
                    UserMother.createModel(uuid = "5"),
                    UserMother.createModel(uuid = "6"),
                    UserMother.createModel(uuid = "7"),
                    UserMother.createModel(uuid = "8"),
                    UserMother.createModel(uuid = "9"),
                ).right()

            renderScreen()
            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }

    @Test
    fun `GIVEN loaded screen WHEN delete first user THEN idle state without deleted user`() =
        runTest {
            coEvery { getUsersListUseCase(any()) } returns
                listOf(
                    UserMother.createModel(uuid = "1", email = "deletedemail@gmail.com"),
                    UserMother.createModel(uuid = "2", email = "newfirstuser@gmail.com"),
                    UserMother.createModel(uuid = "3"),
                    UserMother.createModel(uuid = "4"),
                    UserMother.createModel(uuid = "5"),
                    UserMother.createModel(uuid = "6"),
                    UserMother.createModel(uuid = "7"),
                    UserMother.createModel(uuid = "8"),
                    UserMother.createModel(uuid = "9"),
                ).right()
            coEvery { deleteUserUseCase(any()) } returns Unit.right()

            renderScreen()
            advanceUntilIdle()
            composeTestRule
                .onNodeWithTag("1-delete")
                .performTouchInput {
                    click()
                }
            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }

    @Test
    fun `GIVEN loaded screen WHEN scrolling THEN idle state with new data`() =
        runTest {
            coEvery { getUsersListUseCase(any()) } returnsMany
                listOf(
                    listOf(
                        UserMother.createModel(uuid = "1"),
                        UserMother.createModel(uuid = "2"),
                        UserMother.createModel(uuid = "3"),
                        UserMother.createModel(uuid = "4"),
                        UserMother.createModel(uuid = "5"),
                        UserMother.createModel(uuid = "6"),
                        UserMother.createModel(uuid = "7"),
                        UserMother.createModel(uuid = "8"),
                        UserMother.createModel(uuid = "9"),
                    ).right(),
                    listOf(
                        UserMother.createModel(uuid = "16", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "17", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "18", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "19", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "20", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "21", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "22", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "23", name = UserName(first = "Paco", last = "Doe")),
                        UserMother.createModel(uuid = "24", name = UserName(first = "Paco", last = "Doe")),
                    ).right(),
                )

            coEvery { deleteUserUseCase(any()) } returns Unit.right()

            renderScreen()
            advanceUntilIdle()
            composeTestRule
                .onNodeWithTag("userList")
                .performTouchInput {
                    swipeUp()
                }
            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }

    @Test
    fun `GIVEN loaded screen WHEN apply text on filter THEN users get filtered`() =
        runTest {
            coEvery { getUsersListUseCase(any()) } returns
                listOf(
                    UserMother.createModel(uuid = "1"),
                    UserMother.createModel(uuid = "2", email = "adefff@gmail"),
                    UserMother.createModel(uuid = "3"),
                    UserMother.createModel(uuid = "4"),
                    UserMother.createModel(uuid = "5"),
                    UserMother.createModel(uuid = "6", email = "adefff@gmail"),
                    UserMother.createModel(uuid = "7"),
                    UserMother.createModel(uuid = "8"),
                    UserMother.createModel(uuid = "9"),
                ).right()

            renderScreen()
            advanceUntilIdle()
            composeTestRule
                .onNodeWithTag("searchField")
                .performTextInput("ade")
            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }

    private fun renderScreen() {
        composeTestRule.setContent {
            UsersScreen(viewModel)
        }
    }
}
