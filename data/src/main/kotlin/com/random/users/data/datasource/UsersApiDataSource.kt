package com.random.users.data.datasource

import com.random.users.api.service.UsersApi
import com.random.users.domain.models.UsersErrors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UsersApiDataSource
    @Inject
    constructor(
        private val usersApi: UsersApi,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : UsersRemoteDataSource {
        override suspend fun getUsers(
            page: Int,
            results: Int,
            seed: String?,
        ) = withContext(dispatcher) {
            usersApi
                .getUsers(
                    page = page,
                    results = results,
                    seed = seed,
                ).mapLeft { UsersErrors.NetworkError }
        }
    }
