package com.random.users.users.navigation

import android.net.Uri
import android.os.Bundle
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.random.user.presentation.navigation.BaseNavRoutes
import com.random.user.presentation.navigation.FeatureNavigation
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.users.model.UserUiModel
import com.random.users.users.screen.UserDetailScreen
import com.random.users.users.screen.UsersScreen
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.reflect.typeOf

class UsersNavigationImpl
    @Inject
    constructor() : FeatureNavigation {
        override fun registerNavGraph(
            navGraphBuilder: NavGraphBuilder,
            navController: NavHostController,
        ) {
            navGraphBuilder.navigation<BaseNavRoutes.Users>(
                startDestination = UsersRoute.Home,
            ) {
                composable<UsersRoute.Home> {
                    RandomUsersTheme {
                        UsersScreen(navController = navController)
                    }
                }
                composable<UsersRoute.UserDetail>(
                    typeMap = mapOf(typeOf<UserUiModel>() to UserParamType),
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(300),
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(300),
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(300),
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(300),
                        )
                    },
                ) { backStackEntry ->
                    val route = backStackEntry.toRoute<UsersRoute.UserDetail>()
                    UserDetailScreen(
                        navController = navController,
                        user = route.user,
                    )
                }
            }
        }
    }

val UserParamType =
    object : NavType<UserUiModel>(
        isNullableAllowed = false,
    ) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): UserUiModel? = bundle.getString(key)?.let { parseValue(it) }

        override fun put(
            bundle: Bundle,
            key: String,
            value: UserUiModel,
        ) {
            bundle.putString(key, serializeAsValue(value))
        }

        override fun parseValue(value: String): UserUiModel {
            val decodedValue = Uri.decode(value)
            return Json.decodeFromString(decodedValue)
        }

        override fun serializeAsValue(value: UserUiModel): String {
            val jsonString = Json.encodeToString(value)
            return Uri.encode(jsonString)
        }
    }
