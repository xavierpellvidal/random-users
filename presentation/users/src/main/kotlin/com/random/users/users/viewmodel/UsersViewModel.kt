package com.random.users.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import com.random.users.users.contract.UserUiState
import com.random.users.users.contract.UsersEvent
import com.random.users.users.contract.UsersScreenUiState
import com.random.users.users.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
    @Inject
    constructor(
        private val getUserListUseCase: GetUserListUseCase,
        private val deleteUserUseCase: DeleteUserUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<UsersScreenUiState> =
            MutableStateFlow(
                UsersScreenUiState(),
            )
        val uiState: StateFlow<UsersScreenUiState> = _uiState

        private var currentPage: Int = 0
        private var userList: List<UserUiState> = emptyList()

        init {
            handleEvent(UsersEvent.OnLoadUsers)
        }

        fun handleEvent(event: UsersEvent) {
            when (event) {
                is UsersEvent.OnLoadUsers -> {
                    loadUsers()
                }

                is UsersEvent.OnDeleteUser -> {
                    deleteUser(uuid = event.uuid)
                }

                is UsersEvent.OnFilterUsers -> {
                    filterUsers(event.filterText)
                }
            }
        }

        private fun loadUsers() {
            _uiState.update { state ->
                state.copy(contentState = UsersScreenUiState.ContentState.Loading)
            }
            viewModelScope.launch {
                getUserListUseCase(page = currentPage)
                    .fold(
                        ifLeft = {
                            // TODO send error loading users
                        },
                        ifRight = { newUsers ->
                            currentPage++
                            userList = (userList + newUsers.toUiState()).distinctBy { it.user.uuid }
                            applyUserListToState()
                        },
                    ).also {
                        _uiState.update { state ->
                            state.copy(contentState = UsersScreenUiState.ContentState.Idle)
                        }
                    }
            }
        }

        private fun deleteUser(uuid: String) {
            userList =
                userList.updateUser(uuid) { user ->
                    user.copy(userState = UserUiState.ContentState.Deleting)
                }
            applyUserListToState()
            viewModelScope.launch {
                deleteUserUseCase(uuid = uuid)
                    .fold(
                        ifLeft = {
                            userList =
                                userList.updateUser(uuid) { user ->
                                    user.copy(userState = UserUiState.ContentState.Idle)
                                }
                            // TODO send error deleting movie
                        },
                        ifRight = { _ ->
                            userList = userList.filterNot { it.user.uuid == uuid }
                        },
                    ).also {
                        applyUserListToState()
                    }
            }
        }

        private fun filterUsers(filterText: String) {
            _uiState.update { state ->
                state.copy(
                    users = userList.applyTextFilter(filterText),
                    filterText = filterText,
                    contentState =
                        if (filterText.isBlank()) {
                            UsersScreenUiState.ContentState.Idle
                        } else {
                            UsersScreenUiState.ContentState.Filtered
                        },
                )
            }
        }

        private fun applyUserListToState() =
            _uiState.update { currentState ->
                currentState.copy(
                    users =
                        if (currentState.contentState == UsersScreenUiState.ContentState.Filtered) {
                            userList.applyTextFilter(currentState.filterText)
                        } else {
                            userList
                        },
                )
            }

        private fun List<UserUiState>.applyTextFilter(filter: String): List<UserUiState> =
            if (filter.isBlank()) {
                this
            } else {
                this.filter { userUiState ->
                    val user = userUiState.user
                    user.name.first.contains(filter, ignoreCase = true) ||
                        user.name.last.contains(filter, ignoreCase = true) ||
                        user.email.contains(filter, ignoreCase = true)
                }
            }

        private fun List<UserUiState>.updateUser(
            uuid: String,
            updateUser: (UserUiState) -> UserUiState,
        ): List<UserUiState> =
            map { user ->
                if (user.user.uuid == uuid) updateUser(user) else user
            }
    }
