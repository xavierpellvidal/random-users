package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.api.model.RandomUserResponse
import com.random.users.domain.models.UsersErrors

interface UsersRemoteDataSource {
    suspend fun getUsers(
        page: Int,
        results: Int,
        seed: String,
    ): Either<UsersErrors, RandomUserResponse>
}
