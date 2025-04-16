package com.random.users.users.mapper

import com.random.users.domain.models.User
import com.random.users.users.contract.UserUiState

fun List<User>.toUiState(): List<UserUiState> =
    map { user ->
        UserUiState(
            user = user,
        )
    }
