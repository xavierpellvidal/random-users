package com.random.users.users.contract

import androidx.compose.runtime.Immutable
import com.random.users.users.model.UserUiModel

@Immutable
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

@Immutable
data class UserUiState(
    val user: UserUiModel,
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

sealed class UsersErrorUiEventsState {
    data object Idle : UsersErrorUiEventsState()

    data object DeleteError : UsersErrorUiEventsState()

    data object LoadUsersError : UsersErrorUiEventsState()

    data object UnknownError : UsersErrorUiEventsState()
}
