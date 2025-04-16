package com.random.users.domain.repository

import arrow.core.Either
import com.random.users.domain.models.User
import com.random.users.domain.models.UsersErrors

interface UsersRepository {
    suspend fun getUsers(
        page: Int,
        results: Int,
        seed: String?,
    ): Either<UsersErrors, List<User>>

    suspend fun deleteUser(uuid: String): Either<UsersErrors, Unit>

    suspend fun getSeed(): Either<UsersErrors, String?>

    suspend fun saveSeed(seed: String): Either<UsersErrors, Unit>
}
