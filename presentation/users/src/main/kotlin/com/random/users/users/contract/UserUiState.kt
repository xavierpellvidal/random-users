package com.random.users.users.contract

import com.random.users.domain.models.User

data class UserUiState(
    val user: User,
    val userState: ContentState = ContentState.Idle,
) {
    sealed interface ContentState {
        data object Idle : ContentState

        data object Deleting : ContentState
    }
}
