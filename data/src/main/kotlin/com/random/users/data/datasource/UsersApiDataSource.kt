package com.random.users.data.datasource

import com.random.users.api.service.UsersApi
import com.random.users.domain.models.UserErrors
import javax.inject.Inject

internal class UsersApiDataSource
    @Inject
    constructor(
        private val usersApi: UsersApi,
    ) : UsersRemoteDataSource {
        override suspend fun getUsers(
            page: Int,
            results: Int,
            seed: String,
        ) = usersApi
            .getUsers(
                page = page,
                results = results,
                seed = seed,
            ).mapLeft { UserErrors.NetworkError }
    }
