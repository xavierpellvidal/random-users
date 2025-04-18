
package com.random.users.users.screenshot

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.onRoot
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.random.users.test.rules.createRoborazziRule
import com.random.users.test.rules.createScreenshotTestComposeRule
import com.random.users.users.mapper.toUiModel
import com.random.users.users.mother.UserMother
import com.random.users.users.screen.UserDetailScreen
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
internal class UserDetailScreenTest {
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
    fun `GIVEN user model WHEN load screen THEN correct user info shown`() =
        runTest {
            composeTestRule.setContent {
                UserDetailScreen(
                    user = UserMother.createModel().toUiModel(),
                    navController = rememberNavController(),
                )
            }

            advanceUntilIdle()

            composeTestRule.onRoot().captureRoboImage()
        }
}
