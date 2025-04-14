package com.random.users.users.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.random.user.presentation.navigation.BaseNavRoutes
import com.random.user.presentation.navigation.FeatureNavigation
import com.random.users.users.screen.UsersMainScreen
import javax.inject.Inject

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
                    UsersMainScreen()
                }
            }
        }
    }
