
package com.random.users.users.screenshot

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.random.users.test.model.getUserListResponsePage1Json
import com.random.users.test.rules.createRoborazziRule
import com.random.users.test.rules.createScreenshotTestComposeRule
import com.random.users.users.screen.UsersScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import javax.inject.Inject
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    qualifiers = RobolectricDeviceQualifiers.Pixel4a,
    application = HiltTestApplication::class,
    sdk = [28],
)
internal class UsersScreenshotTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createScreenshotTestComposeRule()

    @get:Rule(order = 2)
    val roborazziRule =
        createRoborazziRule(composeTestRule = composeTestRule, captureType = RoborazziRule.CaptureType.None)

    @get:Rule(order = 3)
    val instantRule = InstantTaskExecutorRule()

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.start(8080)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN getUsersListUseCase returns users WHEN load screen THEN idle state with data`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

            renderScreen()
            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }

    @Test
    fun `GIVEN loaded screen WHEN delete first user THEN idle state without deleted user`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

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
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

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
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserListResponsePage1Json))

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
            UsersScreen()
        }
    }
}
