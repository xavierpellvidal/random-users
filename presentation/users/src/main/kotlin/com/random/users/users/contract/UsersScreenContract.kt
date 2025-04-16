package com.random.users.users.contract

import com.random.users.domain.models.User

data class UsersScreenUiState(
    val users: List<UserUiState> = emptyList(),
    val filterText: String = "",
    val contentState: ContentState = ContentState.Idle,
) {
    sealed interface ContentState {
        data object Idle : ContentState

        data object Filtered : ContentState

        data object Loading : ContentState
    }
}

data class UserUiState(
    val user: User,
    val userState: ContentState = ContentState.Idle,
) {
    sealed interface ContentState {
        data object Idle : ContentState

        data object Deleting : ContentState
    }
}

sealed interface UsersEvent {
    data object OnLoadUsers : UsersEvent

    data class OnFilterUsers(
        val filterText: String,
    ) : UsersEvent

    data class OnDeleteUser(
        val uuid: String,
    ) : UsersEvent
}
