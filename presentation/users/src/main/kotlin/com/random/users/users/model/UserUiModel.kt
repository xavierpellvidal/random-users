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
) {
    companion object {
        fun toPreviewData() =
            UserUiModel(
                uuid = "550e8400-e29b-41d4-a716-446655440000",
                name =
                    UserNameUiModel(
                        first = "María",
                        last = "García",
                    ),
                location =
                    UserLocationUiModel(
                        street =
                            UserStreetUiModel(
                                number = 123,
                                name = "Calle Mayor",
                            ),
                        city = "Madrid",
                        state = "Madrid",
                    ),
                email = "maria.garcia@example.com",
                phone = "+34 612 345 678",
                gender = "female",
                picture =
                    UserPictureUiModel(
                        medium = "https://randomuser.me/api/portraits/women/42.jpg",
                        thumbnail = "https://randomuser.me/api/portraits/thumb/women/42.jpg",
                    ),
            )
    }
}

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
