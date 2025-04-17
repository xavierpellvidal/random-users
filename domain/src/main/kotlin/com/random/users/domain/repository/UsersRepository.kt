package com.random.users.domain.repository

import arrow.core.Either
import com.random.users.domain.models.User
import com.random.users.domain.models.UsersErrors

interface UsersRepository {
    suspend fun getUsers(
        page: Int,
        results: Int,
    ): Either<UsersErrors, List<User>>

    suspend fun getDeletedUsers(): Either<UsersErrors, List<String>>

    suspend fun deleteUser(uuid: String): Either<UsersErrors, Unit>
}
