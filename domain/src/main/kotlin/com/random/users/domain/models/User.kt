package com.random.users.domain.models

data class User(
    val uuid: String,
    val name: UserName,
    val location: UserLocation,
    val email: String,
    val phone: String,
    val gender: String,
    val picture: UserPicture,
)

data class UserName(
    val first: String,
    val last: String,
)

data class UserLocation(
    val street: UserStreet,
    val city: String,
    val state: String,
)

data class UserStreet(
    val number: Int,
    val name: String,
)

data class UserPicture(
    val medium: String,
    val thumbnail: String,
)
