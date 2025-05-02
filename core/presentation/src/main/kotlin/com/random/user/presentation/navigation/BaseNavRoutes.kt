package com.random.user.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface BaseNavRoutes {
    @Serializable
    data object MainGraph : BaseNavRoutes

    @Serializable
    data object Users : BaseNavRoutes
}
