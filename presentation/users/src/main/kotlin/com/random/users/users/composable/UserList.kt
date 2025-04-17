package com.random.users.users.composable

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import com.random.users.users.contract.UsersScreenUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    state: UsersScreenUiState,
    onDeleteUser: (String) -> Unit,
    onLoadUsers: () -> Unit,
) {
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom() }
    }

    LaunchedEffect(
        reachedBottom,
        state.contentState,
    ) {
        snapshotFlow { reachedBottom }
            .distinctUntilChanged()
            .filter { it && state.contentState is UsersScreenUiState.ContentState.Idle }
            .collect { onLoadUsers() }
    }

    LaunchedEffect(
        state.contentState,
        state.filterText,
    ) {
        if (state.contentState is UsersScreenUiState.ContentState.Filtered) {
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
    ) {
        itemsIndexed(
            items = state.users,
            key = { _, user -> user.user.uuid },
        ) { _, user ->
            UserCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .animateItem(),
                user = user,
                onDeleteUser = { uuid -> onDeleteUser(uuid) },
            )
        }
        if (state.contentState is UsersScreenUiState.ContentState.Loading) {
            item(key = "loading") {
                LoadingItem()
            }
        }
    }
}

@Composable
private fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            strokeWidth = 2.dp,
        )
    }
}

private fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    if (layoutInfo.visibleItemsInfo.isEmpty()) return true
    val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
    return lastVisibleItem.index >= layoutInfo.totalItemsCount - buffer - 1
}

@PreviewLightDark
@Composable
fun UserListPreview() {
    RandomUsersTheme {
        UserList(
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
                                userState = UserUiState.ContentState.Deleting,
                            ),
                        ),
                    filterText = "",
                    contentState = UsersScreenUiState.ContentState.Loading,
                ),
            onDeleteUser = {},
            onLoadUsers = {},
        )
    }
}
