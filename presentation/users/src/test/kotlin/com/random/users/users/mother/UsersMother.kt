package com.random.users.users.mother

import com.random.users.domain.models.User
import com.random.users.domain.models.UserLocation
import com.random.users.domain.models.UserName
import com.random.users.domain.models.UserPicture
import com.random.users.domain.models.UserStreet
import java.util.UUID

object UsersMother {
    fun create(
        uuid: String = UUID.randomUUID().toString(),
        firstName: String = "John",
        lastName: String = "Doe",
        streetName: String = "Principal",
        streetNumber: Int = 123,
        city: String = "Madrid",
        state: String = "Madrid",
        email: String = "john.doe@example.com",
        phone: String = "123-456-7890",
        gender: String = "male",
        pictureMedium: String = "https://randomuser.me/api/portraits/men/1.jpg",
        pictureThumbnail: String = "https://randomuser.me/api/portraits/thumb/men/1.jpg",
    ): User =
        User(
            uuid = uuid,
            name =
                UserName(
                    first = firstName,
                    last = lastName,
                ),
            location =
                UserLocation(
                    street =
                        UserStreet(
                            number = streetNumber,
                            name = streetName,
                        ),
                    city = city,
                    state = state,
                ),
            email = email,
            phone = phone,
            gender = gender,
            picture =
                UserPicture(
                    medium = pictureMedium,
                    thumbnail = pictureThumbnail,
                ),
        )

    fun createList(size: Int = 15): List<User> =
        List(size) { index ->
            val gender = if (index % 2 == 0) "male" else "female"
            val imageIndex = (index % 98) + 1

            create(
                uuid = index.toString(),
                firstName = "Name${index + 1}",
                lastName = "Surname${index + 1}",
                email = "user${index + 1}@example.com",
                pictureMedium = "https://randomuser.me/api/portraits/$gender/$imageIndex.jpg",
                pictureThumbnail = "https://randomuser.me/api/portraits/thumb/$gender/$imageIndex.jpg",
                phone = "123-456-${1000 + index}",
                streetName = "Street ${index + 1}",
                streetNumber = 100 + index,
                city = "City ${index % 10 + 1}",
                state = "State ${index % 5 + 1}",
                gender = gender,
            )
        }
}
