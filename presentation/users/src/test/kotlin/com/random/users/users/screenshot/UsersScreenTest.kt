//package com.random.users.users.screenshot
//
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
//import com.random.user.presentation.ui.theme.RandomUsersTheme
//import com.random.users.domain.usecase.DeleteUserUseCase
//import com.random.users.domain.usecase.GetUserListUseCase
//import com.random.users.users.screen.UsersScreen
//import com.random.users.users.viewmodel.UsersViewModel
//import io.mockk.mockk
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.runner.RunWith
//import org.robolectric.annotation.Config
//import org.robolectric.annotation.GraphicsMode
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//@GraphicsMode(GraphicsMode.Mode.NATIVE)
//@Config(qualifiers = RobolectricDeviceQualifiers.Pixel4a)
//internal class UsersScreenTest {
//    private val getUsersListUseCase: GetUserListUseCase = mockk()
//    private val deleteUserUseCase: DeleteUserUseCase = mockk()
//    private val viewModel: UsersViewModel by lazy {
//        UsersViewModel(getUsersListUseCase, deleteUserUseCase)
//    }
//
////    @get:Rule
////    val composeTestRule = createScreenshotTestComposeRule()
////
////    @get:Rule
////    val roborazziRule =
////        createRoborazziRule(composeTestRule = composeTestRule, captureType = RoborazziRule.CaptureType.None)
////
////    @get:Rule
////    val instantRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(StandardTestDispatcher())
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
////    @Test
////    fun `GIVEN getUsersListUseCase returns users WHEN load screen THEN fired then loading state`() =
////        runTest {
////            coEvery { getUsersListUseCase(any()) } returns UsersMother.createList(20).right()
////
////            renderScreen()
////
////            composeTestRule.onRoot().captureRoboImage()
////        }
//
////    private fun renderScreen() {
////        composeTestRule.setContent {
////            RandomUsersTheme {
////                UsersScreen(
////                    viewModel = viewModel,
////                )
////            }
////        }
////    }
//}
