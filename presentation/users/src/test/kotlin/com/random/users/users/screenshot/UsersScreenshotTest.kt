
package com.random.users.users.screenshot

import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import arrow.core.left
import arrow.core.right
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.test.rules.MainDispatcherRule
import com.random.users.test.rules.createRoborazziRule
import com.random.users.test.rules.createScreenshotTestComposeRule
import com.random.users.users.mother.UserMother
import com.random.users.users.screen.UsersScreen
import com.random.users.users.viewmodel.UsersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    qualifiers = RobolectricDeviceQualifiers.Pixel7,
    sdk = [34],
)
internal class UsersScreenshotTest {
    @get:Rule(order = 0)
    var mainRule: TestRule = MainDispatcherRule()

    @get:Rule(order = 1)
    val composeTestRule = createScreenshotTestComposeRule()

    @get:Rule(order = 2)
    val roborazziRule =
        createRoborazziRule(composeTestRule = composeTestRule, captureType = RoborazziRule.CaptureType.None)

    private val getUsersListUseCase: GetUserListUseCase = mockk()
    private val deleteUserUseCase: DeleteUserUseCase = mockk()
    lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        viewModel = UsersViewModel(getUsersListUseCase, deleteUserUseCase)
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
    fun `GIVEN getUsersListUseCase returns users WHEN delete first user THEN idle state without deleted user`() =
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

    @Test
    fun `GIVEN error in loading users WHEN load screen THEN error state`() =
        runTest {
            coEvery { getUsersListUseCase(any()) } returns
                UsersErrors.NetworkError.left()

            renderScreen()
            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }

    private fun renderScreen() {
        composeTestRule.setContent {
            UsersScreen(viewModel)
        }
    }
}
