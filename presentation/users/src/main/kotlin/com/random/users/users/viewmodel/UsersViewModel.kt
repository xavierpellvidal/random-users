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
            viewModelScope.launch {
                getUserListUseCase(page = currentPage).fold(
                    ifLeft = {
                        // TODO send error loading users
                    },
                    ifRight = { newUsers ->
                        currentPage++
                        _uiState.update { state ->
                            state.copy(
                                users = state.users + newUsers.toUiState(),
                            )
                        }
                    },
                )
            }
        }

        private fun deleteUser(uuid: String) {
            _uiState.update { state ->
                state.copy(
                    users =
                        state.users.updateUser(uuid) { user ->
                            user.copy(
                                userState = UserUiState.ContentState.Deleting,
                            )
                        },
                )
            }
            viewModelScope.launch {
                deleteUserUseCase(uuid = uuid).fold(
                    ifLeft = {
                        _uiState.update { state ->
                            state.copy(
                                users =
                                    state.users.updateUser(uuid) { user ->
                                        user.copy(userState = UserUiState.ContentState.Idle)
                                    },
                            )
                        }
                        // TODO send error deleting movie
                    },
                    ifRight = { _ ->
                        _uiState.update { state ->
                            state.copy(
                                users = state.users.filterNot { it.user.uuid == uuid },
                            )
                        }
                    },
                )
            }
        }

        private fun filterUsers(filterText: String) {
            _uiState.update { state ->
                state.copy(
                    users = state.users.applyTextFilter(filterText),
                    contentState =
                        if (filterText.isBlank()) {
                            UsersScreenUiState.ContentState.Idle
                        } else {
                            UsersScreenUiState.ContentState.Filtered
                        },
                )
            }
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
