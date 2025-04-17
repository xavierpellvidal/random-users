package com.random.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.random.user.navigation.viewmodel.NavigationViewModel
import com.random.user.presentation.navigation.BaseNavRoutes

@Composable
fun BaseProjectApplicationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NavigationViewModel = hiltViewModel(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BaseNavRoutes.MainGraph,
    ) {
        navigation<BaseNavRoutes.MainGraph>(
            startDestination = BaseNavRoutes.Users,
        ) {
            viewModel.subNavigation.forEach { subNavigation ->
                subNavigation.registerNavGraph(
                    navGraphBuilder = this,
                    navController = navController,
                )
            }
        }
    }
}
