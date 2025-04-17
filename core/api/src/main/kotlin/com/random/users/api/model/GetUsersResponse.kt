package com.random.users.api.model

import com.google.gson.annotations.SerializedName

data class RandomUserResponse(
    @SerializedName("results") val results: List<UserDto>,
    @SerializedName("info") val info: ResponseInfoDto,
)

data class UserDto(
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: NameDto,
    @SerializedName("location") val location: LocationDto,
    @SerializedName("email") val email: String,
    @SerializedName("login") val login: LoginDto,
    @SerializedName("dob") val dob: DateInfoDto,
    @SerializedName("registered") val registered: DateInfoDto,
    @SerializedName("phone") val phone: String,
    @SerializedName("cell") val cell: String,
    @SerializedName("id") val id: IdDto,
    @SerializedName("picture") val picture: PictureDto,
    @SerializedName("nat") val nat: String,
)

data class NameDto(
    @SerializedName("title") val title: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String,
)

data class LocationDto(
    @SerializedName("street") val street: StreetDto,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("country") val country: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("coordinates") val coordinates: CoordinatesDto,
    @SerializedName("timezone") val timezone: TimezoneDto,
)

data class StreetDto(
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String,
)

data class CoordinatesDto(
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
)

data class TimezoneDto(
    @SerializedName("offset") val offset: String,
    @SerializedName("description") val description: String,
)

data class LoginDto(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("salt") val salt: String,
    @SerializedName("md5") val md5: String,
    @SerializedName("sha1") val sha1: String,
    @SerializedName("sha256") val sha256: String,
)

data class DateInfoDto(
    @SerializedName("date") val date: String,
    @SerializedName("age") val age: Int,
)

data class IdDto(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: String,
)

data class PictureDto(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("thumbnail") val thumbnail: String,
)

data class ResponseInfoDto(
    @SerializedName("seed") val seed: String,
    @SerializedName("results") val results: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("version") val version: String,
)
