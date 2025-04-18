package com.random.users.data.mapper

import com.random.users.api.model.UserDto
import com.random.users.domain.models.User
import com.random.users.domain.models.UserLocation
import com.random.users.domain.models.UserName
import com.random.users.domain.models.UserPicture
import com.random.users.domain.models.UserStreet

internal object UserMapper {
    fun List<UserDto>.toDomain() = map { it.toDomain() }

    fun UserDto.toDomain() =
        User(
            uuid = login.uuid,
            name =
                UserName(
                    first = name.first,
                    last = name.last,
                ),
            location =
                UserLocation(
                    street =
                        UserStreet(
                            number = location.street.number,
                            name = location.street.name,
                        ),
                    city = location.city,
                    state = location.state,
                ),
            email = email,
            phone = phone,
            gender = gender,
            picture =
                UserPicture(
                    medium = picture.large,
                    thumbnail = picture.medium,
                ),
        )
}
