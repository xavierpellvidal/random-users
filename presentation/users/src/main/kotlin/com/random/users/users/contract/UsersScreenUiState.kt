package com.random.users.users.contract

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
