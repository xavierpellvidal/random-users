package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.domain.models.UserErrors

interface UsersLocalDataSource {
    fun getSeed(): Either<UserErrors, String?>

    fun saveSeed(seed: String)
}
