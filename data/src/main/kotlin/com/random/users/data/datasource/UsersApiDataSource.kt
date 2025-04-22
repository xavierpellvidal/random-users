package com.random.users.data.datasource

import com.random.users.api.api.UsersApi
import com.random.users.domain.models.UsersErrors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UsersApiDataSource
    @Inject
    constructor(
        private val usersApi: UsersApi,
        private val dispatcher: CoroutineDispatcher,
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
                ).mapLeft {
                    UsersErrors.NetworkError
                }
        }
    }
