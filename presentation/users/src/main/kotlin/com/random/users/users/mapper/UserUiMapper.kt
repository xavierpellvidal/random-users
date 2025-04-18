package com.random.users.users.mapper

import com.random.users.domain.models.User
import com.random.users.domain.models.UserLocation
import com.random.users.domain.models.UserName
import com.random.users.domain.models.UserPicture
import com.random.users.domain.models.UserStreet
import com.random.users.users.contract.UserUiState
import com.random.users.users.model.UserLocationUiModel
import com.random.users.users.model.UserNameUiModel
import com.random.users.users.model.UserPictureUiModel
import com.random.users.users.model.UserStreetUiModel
import com.random.users.users.model.UserUiModel

internal fun List<User>.toUiState(): List<UserUiState> =
    map { user ->
        UserUiState(
            user = user.toUiModel(),
        )
    }

private fun User.toUiModel() =
    UserUiModel(
        uuid = uuid,
        name = name.toUiModel(),
        location = location.toUiModel(),
        email = email,
        phone = phone,
        gender = gender,
        picture = picture.toUiModel(),
    )

private fun UserName.toUiModel() =
    UserNameUiModel(
        first = first,
        last = last,
    )

private fun UserLocation.toUiModel() =
    UserLocationUiModel(
        street = street.toUiModel(),
        city = city,
        state = state,
    )

private fun UserStreet.toUiModel() =
    UserStreetUiModel(
        number = number,
        name = name,
    )

private fun UserPicture.toUiModel() =
    UserPictureUiModel(
        medium = medium,
        thumbnail = thumbnail,
    )
