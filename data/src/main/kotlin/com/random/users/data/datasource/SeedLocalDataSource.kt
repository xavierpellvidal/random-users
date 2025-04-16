package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.domain.models.UsersErrors

interface SeedLocalDataSource {
    suspend fun getSeed(): Either<UsersErrors, String?>

    suspend fun saveSeed(seed: String): Either<UsersErrors, Unit>
}
