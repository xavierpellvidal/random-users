package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.api.model.RandomUserResponse
import com.random.users.domain.models.UserErrors

interface UsersRemoteDataSource {
    suspend fun getUsers(
        page: Int,
        results: Int,
        seed: String,
    ): Either<UserErrors, RandomUserResponse>
}
