package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.domain.models.UserErrors

interface SeedLocalDataSource {
    fun getSeed(): Either<UserErrors, String?>

    fun saveSeed(seed: String)
}
