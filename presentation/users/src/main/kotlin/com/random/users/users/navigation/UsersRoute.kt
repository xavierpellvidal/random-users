package com.random.users.users.navigation

import kotlinx.serialization.Serializable

sealed class UsersRoute {
    @Serializable
    data object Home : UsersRoute()
}
