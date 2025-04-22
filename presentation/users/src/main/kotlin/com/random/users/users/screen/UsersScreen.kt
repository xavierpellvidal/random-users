package com.random.users.users.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.users.composable.UserList
import com.random.users.users.composable.UserSearchView
import com.random.users.users.contract.UserUiState
import com.random.users.users.contract.UsersErrorUiEventsState
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import com.random.users.users.model.UserUiModel
import com.random.users.users.navigation.UsersRoute
import com.random.users.users.viewmodel.UsersViewModel
import kotlinx.coroutines.flow.Flow

@Composable
internal fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HandleOneTimeEvents(viewModel.uiEventsState)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        UsersContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onDeleteUser = { viewModel.handleEvent(UsersEvent.OnDeleteUser(uuid = it)) },
            onLoadUsers = { viewModel.handleEvent(UsersEvent.OnLoadUsers) },
            onFilterUsers = { viewModel.handleEvent(UsersEvent.OnFilterUsers(filterText = it)) },
            onUserClick = { navController.navigate(UsersRoute.UserDetail(user = it)) },
        )
    }
}

@Composable
private fun UsersContent(
    modifier: Modifier = Modifier,
    state: UsersScreenUiState,
    onDeleteUser: (String) -> Unit,
    onLoadUsers: () -> Unit,
    onFilterUsers: (String) -> Unit,
    onUserClick: (UserUiModel) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(20.dp),
    ) {
        UserSearchView(
            onValueChange = { onFilterUsers(it) },
        )
        UserList(
            state = state,
            onDeleteUser = { uuid -> onDeleteUser(uuid) },
            onLoadUsers = { onLoadUsers() },
            onUserClick = { user -> onUserClick(user) },
        )
    }
}

@Composable
private fun HandleOneTimeEvents(uiEventsState: Flow<UsersErrorUiEventsState>) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current
    LaunchedEffect(uiEventsState) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            uiEventsState.collect { event ->
                showError(state = event, context = context)
            }
        }
    }
}

private fun showError(
    state: UsersErrorUiEventsState,
    context: Context,
) {
    when (state) {
        is UsersErrorUiEventsState.DeleteError -> {
            Toast.makeText(context, "Error deleting user", Toast.LENGTH_SHORT).show()
        }

        is UsersErrorUiEventsState.LoadUsersError -> {
            Toast.makeText(context, "Error loading users", Toast.LENGTH_SHORT).show()
        }

        else -> {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}

@PreviewLightDark
@Composable
private fun UsersScreenPreview() {
    RandomUsersTheme {
        UsersContent(
            state =
                UsersScreenUiState(
                    users =
                        listOf(
                            UserUiState(
                                user = UserUiModel.toPreviewData(),
                                userState = UserUiState.ContentState.Idle,
                            ),
                            UserUiState(
                                user = UserUiModel.toPreviewData().copy(uuid = "2"),
                                userState = UserUiState.ContentState.Deleting,
                            ),
                        ),
                ),
            onDeleteUser = {},
            onLoadUsers = {},
            onFilterUsers = {},
            onUserClick = {},
        )
    }
}
