package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.domain.models.UsersErrors

interface UsersLocalDataSource {
    suspend fun getDeletedUsers(): Either<UsersErrors, List<String>>

    suspend fun deleteUser(uuid: String): Either<UsersErrors, Unit>
}
