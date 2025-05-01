package com.random.users.users.contract

import androidx.compose.runtime.Immutable
import com.random.users.users.model.UserUiModel

@Immutable
internal data class UsersScreenUiState(
    val users: List<UserUiState> = emptyList(),
    val filterText: String = "",
    val contentState: ContentState = ContentState.Idle,
) {
    sealed interface ContentState {
        data object Idle : ContentState

        data object Filtered : ContentState

        data object Loading : ContentState

        data object Error : ContentState
    }
}

@Immutable
internal data class UserUiState(
    val user: UserUiModel,
    val userState: ContentState = ContentState.Idle,
) {
    sealed interface ContentState {
        data object Idle : ContentState

        data object Deleting : ContentState
    }
}

internal sealed interface UsersUiEvent {
    data object OnLoadUsers : UsersUiEvent

    data class OnFilterUsers(
        val filterText: String,
    ) : UsersUiEvent

    data class OnDeleteUser(
        val uuid: String,
    ) : UsersUiEvent
}

internal sealed interface UsersErrorUiState {
    data object DeleteError : UsersErrorUiState

    data object LoadUsersError : UsersErrorUiState

    data object UnknownError : UsersErrorUiState
}
