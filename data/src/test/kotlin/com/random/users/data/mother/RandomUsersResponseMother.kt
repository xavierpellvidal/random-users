package com.random.users.data.mother

import com.random.users.api.model.CoordinatesDto
import com.random.users.api.model.DateInfoDto
import com.random.users.api.model.IdDto
import com.random.users.api.model.LocationDto
import com.random.users.api.model.LoginDto
import com.random.users.api.model.NameDto
import com.random.users.api.model.PictureDto
import com.random.users.api.model.RandomUserResponse
import com.random.users.api.model.ResponseInfoDto
import com.random.users.api.model.StreetDto
import com.random.users.api.model.TimezoneDto
import com.random.users.api.model.UserDto

object RandomUsersResponseMother {
    fun createModel(
        results: List<UserDto> = listOf(UserDtoMother.createModel()),
        info: ResponseInfoDto = ResponseInfoDtoMother.createModel(),
    ): RandomUserResponse = RandomUserResponse(results, info)
}

object ResponseInfoDtoMother {
    fun createModel(
        seed: String = "default-seed",
        results: Int = 10,
        page: Int = 1,
        version: String = "1.0",
    ): ResponseInfoDto =
        ResponseInfoDto(
            seed = seed,
            results = results,
            page = page,
            version = version,
        )
}

object UserDtoMother {
    fun createModel(
        gender: String = "male",
        name: NameDto = NameDtoMother.createModel(),
        location: LocationDto = LocationDtoMother.createModel(),
        email: String = "example@example.com",
        login: LoginDto = LoginDtoMother.createModel(),
        dob: DateInfoDto = DateInfoDtoMother.createModel(),
        registered: DateInfoDto = DateInfoDtoMother.createModel(),
        phone: String = "123-456-789",
        cell: String = "987-654-321",
        id: IdDto = IdDtoMother.createModel(),
        picture: PictureDto = PictureDtoMother.createModel(),
        nat: String = "US",
    ): UserDto =
        UserDto(
            gender = gender,
            name = name,
            location = location,
            email = email,
            login = login,
            dob = dob,
            registered = registered,
            phone = phone,
            cell = cell,
            id = id,
            picture = picture,
            nat = nat,
        )
}

object NameDtoMother {
    fun createModel(
        title: String = "Mr",
        first: String = "John",
        last: String = "Doe",
    ): NameDto = NameDto(title, first, last)
}

object LocationDtoMother {
    fun createModel(
        street: StreetDto = StreetDtoMother.createModel(),
        city: String = "New York",
        state: String = "NY",
        country: String = "USA",
        postcode: String = "10001",
        coordinates: CoordinatesDto = CoordinatesDtoMother.createModel(),
        timezone: TimezoneDto = TimezoneDtoMother.createModel(),
    ): LocationDto = LocationDto(street, city, state, country, postcode, coordinates, timezone)
}

object StreetDtoMother {
    fun createModel(
        number: Int = 123,
        name: String = "Main Street",
    ): StreetDto = StreetDto(number, name)
}

object CoordinatesDtoMother {
    fun createModel(
        latitude: String = "40.7128",
        longitude: String = "-74.0060",
    ): CoordinatesDto = CoordinatesDto(latitude, longitude)
}

object TimezoneDtoMother {
    fun createModel(
        offset: String = "-05:00",
        description: String = "Eastern Time (US & Canada)",
    ): TimezoneDto = TimezoneDto(offset, description)
}

object LoginDtoMother {
    fun createModel(
        uuid: String = "mock-uuid",
        username: String = "johndoe",
        password: String = "password123",
        salt: String = "salt",
        md5: String = "md5hash",
        sha1: String = "sha1hash",
        sha256: String = "sha256hash",
    ): LoginDto = LoginDto(uuid, username, password, salt, md5, sha1, sha256)
}

object DateInfoDtoMother {
    fun createModel(
        date: String = "2000-01-01T00:00:00Z",
        age: Int = 21,
    ): DateInfoDto = DateInfoDto(date, age)
}

object IdDtoMother {
    fun createModel(
        name: String = "SSN",
        value: String = "123-45-6789",
    ): IdDto = IdDto(name, value)
}

object PictureDtoMother {
    fun createModel(
        large: String = "https://example.com/large.jpg",
        medium: String = "https://example.com/medium.jpg",
        thumbnail: String = "https://example.com/thumbnail.jpg",
    ): PictureDto = PictureDto(large, medium, thumbnail)
}
