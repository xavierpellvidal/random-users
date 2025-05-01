package com.random.users.users.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.random.user.presentation.ui.theme.RandomUsersTheme
import com.random.users.users.contract.UserUiState
import com.random.users.users.contract.UsersScreenUiState
import com.random.users.users.model.UserUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
internal fun UserList(
    modifier: Modifier = Modifier,
    state: UsersScreenUiState,
    onDeleteUser: (String) -> Unit,
    onLoadUsers: () -> Unit,
    onUserClick: (UserUiModel) -> Unit = {},
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
            .collectLatest { onLoadUsers() }
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
        modifier = modifier.fillMaxWidth().testTag("userList"),
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
                        .animateItem()
                        .clickable { onUserClick(user.user) }
                        .testTag(user.user.uuid),
                user = user,
                onDeleteUser = { uuid -> onDeleteUser(uuid) },
            )
        }
        when (state.contentState) {
            is UsersScreenUiState.ContentState.Loading -> {
                item(key = "loading") {
                    LoadingItem()
                }
            }
            is UsersScreenUiState.ContentState.Error -> {
                item(key = "retry") {
                    RetryItem(
                        onRetry = onLoadUsers,
                    )
                }
            }
            else -> {}
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

@Composable
private fun RetryItem(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Retry",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

private fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    if (layoutInfo.visibleItemsInfo.isEmpty()) return true
    val lastVisibleItem = layoutInfo.visibleItemsInfo.last()
    return lastVisibleItem.index >= layoutInfo.totalItemsCount - buffer - 1
}

@PreviewLightDark
@Composable
private fun UserListLoadingPreview() {
    RandomUsersTheme {
        UserList(
            state =
                UsersScreenUiState(
                    users =
                        listOf(
                            UserUiState(
                                user = UserUiModel.toPreviewData().copy(uuid = "1"),
                                userState = UserUiState.ContentState.Idle,
                            ),
                            UserUiState(
                                user = UserUiModel.toPreviewData().copy(uuid = "2"),
                                userState = UserUiState.ContentState.Idle,
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

@PreviewLightDark
@Composable
private fun UserListErrorPreview() {
    RandomUsersTheme {
        UserList(
            state =
                UsersScreenUiState(
                    users =
                        listOf(
                            UserUiState(
                                user = UserUiModel.toPreviewData().copy(uuid = "1"),
                                userState = UserUiState.ContentState.Idle,
                            ),
                            UserUiState(
                                user = UserUiModel.toPreviewData().copy(uuid = "2"),
                                userState = UserUiState.ContentState.Idle,
                            ),
                        ),
                    filterText = "",
                    contentState = UsersScreenUiState.ContentState.Error,
                ),
            onDeleteUser = {},
            onLoadUsers = {},
        )
    }
}
