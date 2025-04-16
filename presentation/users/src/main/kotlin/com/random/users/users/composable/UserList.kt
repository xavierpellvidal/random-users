package com.random.users.users.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.domain.models.User
import com.random.users.domain.models.UserLocation
import com.random.users.domain.models.UserName
import com.random.users.domain.models.UserPicture
import com.random.users.domain.models.UserStreet
import com.random.users.users.contract.UserUiState

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    users: List<UserUiState>,
    onDeleteUser: (String) -> Unit,
    onLoadUsers: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
    ) {
        items(count = users.size) { index ->
            UserCard(
                modifier = Modifier.fillMaxWidth().animateItem(),
                user = users[index],
                onDeleteUser = { uuid -> onDeleteUser(uuid) },
            )
        }
    }
}

@PreviewLightDark
@Composable
fun UserListPreview() {
    RandomUsersTheme {
        UserList(
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
            onDeleteUser = {},
            onLoadUsers = {},
        )
    }
}
