package com.random.users.users.navigation

import com.random.users.users.model.UserUiModel
import kotlinx.serialization.Serializable

internal sealed class UsersRoute {
    @Serializable
    data object Home : UsersRoute()

    @Serializable
    data class UserDetail(
        val user: UserUiModel,
    ) : UsersRoute()
}
