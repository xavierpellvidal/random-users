package com.random.users.users.mother

import com.random.users.domain.models.User
import com.random.users.domain.models.UserLocation
import com.random.users.domain.models.UserName
import com.random.users.domain.models.UserPicture
import com.random.users.domain.models.UserStreet

object UserMother {
    fun createModel(
        uuid: String = "mock-uuid",
        name: UserName = createUserName(),
        location: UserLocation = createUserLocation(),
        email: String = "mock@email.com",
        phone: String = "123-456-789",
        gender: String = "male",
        picture: UserPicture = createUserPicture(),
    ): User =
        User(
            uuid = uuid,
            name = name,
            location = location,
            email = email,
            phone = phone,
            gender = gender,
            picture = picture,
        )

    fun createUserName(
        first: String = "John",
        last: String = "Doe",
    ): UserName = UserName(first, last)

    fun createUserLocation(
        street: UserStreet = createUserStreet(),
        city: String = "Madrid",
        state: String = "Madrid",
    ): UserLocation = UserLocation(street, city, state)

    fun createUserStreet(
        number: Int = 123,
        name: String = "Calle Mayor",
    ): UserStreet = UserStreet(number, name)

    fun createUserPicture(
        medium: String = "https://example.com/medium.jpg",
        thumbnail: String = "https://example.com/thumbnail.jpg",
    ): UserPicture = UserPicture(medium, thumbnail)
}
