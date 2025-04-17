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

fun List<User>.toUiState(): List<UserUiState> =
    map { user ->
        UserUiState(
            user = user.toUiModel(),
        )
    }

fun User.toUiModel() =
    UserUiModel(
        uuid = uuid,
        name = name.toUiModel(),
        location = location.toUiModel(),
        email = email,
        phone = phone,
        gender = gender,
        picture = picture.toUiModel(),
    )

fun UserName.toUiModel() =
    UserNameUiModel(
        first = first,
        last = last,
    )

fun UserLocation.toUiModel() =
    UserLocationUiModel(
        street = street.toUiModel(),
        city = city,
        state = state,
    )

fun UserStreet.toUiModel() =
    UserStreetUiModel(
        number = number,
        name = name,
    )

fun UserPicture.toUiModel() =
    UserPictureUiModel(
        medium = medium,
        thumbnail = thumbnail,
    )
