package com.random.users.users.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.domain.models.User
import com.random.users.domain.models.UserLocation
import com.random.users.domain.models.UserName
import com.random.users.domain.models.UserPicture
import com.random.users.domain.models.UserStreet
import com.random.users.users.composable.UserList
import com.random.users.users.composable.UserSearchView
import com.random.users.users.contract.UserUiState
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import com.random.users.users.viewmodel.UsersViewModel

@Composable
fun UsersScreen(viewModel: UsersViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    UsersContent(
        state = state,
        onDeleteUser = { viewModel.handleEvent(UsersEvent.OnDeleteUser(uuid = it)) },
        onLoadUsers = { viewModel.handleEvent(UsersEvent.OnLoadUsers) },
        onFilterUsers = { viewModel.handleEvent(UsersEvent.OnFilterUsers(filterText = it)) },
    )
}

@Composable
internal fun UsersContent(
    state: UsersScreenUiState,
    onDeleteUser: (String) -> Unit,
    onLoadUsers: () -> Unit,
    onFilterUsers: (String) -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .padding(innerPadding),
        ) {
            UserSearchView(
                onValueChange = { onFilterUsers(it) },
            )
            UserList(
                state = state,
                onDeleteUser = { uuid -> onDeleteUser(uuid) },
                onLoadUsers = { onLoadUsers() },
            )
        }
    }
}

@PreviewLightDark
@Composable
fun UsersScreenPreview() {
    RandomUsersTheme {
        UsersContent(
            state =
                UsersScreenUiState(
                    users =
                        listOf(
                            UserUiState(
                                user =
                                    User(
                                        uuid = "550e8400-e29b-41d4-a716-446655440000",
                                        name =
                                            UserName(
                                                first = "María",
                                                last = "García",
                                            ),
                                        location =
                                            UserLocation(
                                                street =
                                                    UserStreet(
                                                        number = 123,
                                                        name = "Calle Mayor",
                                                    ),
                                                city = "Madrid",
                                                state = "Madrid",
                                            ),
                                        email = "maria.garcia@example.com",
                                        phone = "+34 612 345 678",
                                        gender = "female",
                                        picture =
                                            UserPicture(
                                                medium = "https://randomuser.me/api/portraits/women/42.jpg",
                                                thumbnail = "https://randomuser.me/api/portraits/thumb/women/42.jpg",
                                            ),
                                    ),
                                userState = UserUiState.ContentState.Idle,
                            ),
                            UserUiState(
                                user =
                                    User(
                                        uuid = "550e8400-e29b-41d4-a716-446655440001",
                                        name =
                                            UserName(
                                                first = "Alejandro",
                                                last = "Rodríguez",
                                            ),
                                        location =
                                            UserLocation(
                                                street =
                                                    UserStreet(
                                                        number = 47,
                                                        name = "Avenida Diagonal",
                                                    ),
                                                city = "Barcelona",
                                                state = "Cataluña",
                                            ),
                                        email = "alejandro.rodriguez@example.com",
                                        phone = "+34 633 456 789",
                                        gender = "male",
                                        picture =
                                            UserPicture(
                                                medium = "https://randomuser.me/api/portraits/men/29.jpg",
                                                thumbnail = "https://randomuser.me/api/portraits/thumb/men/29.jpg",
                                            ),
                                    ),
                                userState = UserUiState.ContentState.Idle,
                            ),
                        ),
                ),
            onDeleteUser = {},
            onLoadUsers = {},
            onFilterUsers = {},
        )
    }
}
