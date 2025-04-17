package com.random.users.users.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class UserUiModel(
    val uuid: String,
    val name: UserNameUiModel,
    val location: UserLocationUiModel,
    val email: String,
    val phone: String,
    val gender: String,
    val picture: UserPictureUiModel,
)

@Serializable
@Immutable
data class UserNameUiModel(
    val first: String,
    val last: String,
)

@Serializable
@Immutable
data class UserLocationUiModel(
    val street: UserStreetUiModel,
    val city: String,
    val state: String,
)

@Serializable
@Immutable
data class UserStreetUiModel(
    val number: Int,
    val name: String,
)

@Serializable
@Immutable
data class UserPictureUiModel(
    val medium: String,
    val thumbnail: String,
)
