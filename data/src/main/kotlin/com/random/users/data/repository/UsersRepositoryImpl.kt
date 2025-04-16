package com.random.users.data.repository

import arrow.core.Either
import arrow.core.getOrElse
import com.random.users.data.datasource.SeedLocalDataSource
import com.random.users.data.datasource.UsersLocalDataSource
import com.random.users.data.datasource.UsersRemoteDataSource
import com.random.users.data.mapper.UserMapper.toDomain
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.repository.UsersRepository
import javax.inject.Inject

internal class UsersRepositoryImpl
    @Inject
    constructor(
        private val usersLocalDataSource: UsersLocalDataSource,
        private val usersRemoteDataSource: UsersRemoteDataSource,
        private val seedLocalDataSource: SeedLocalDataSource,
    ) : UsersRepository {
        override suspend fun getUsers(
            page: Int,
            results: Int,
            seed: String?,
        ) = usersRemoteDataSource
            .getUsers(
                page = page,
                results = results,
                seed = seed,
            ).map { users ->
                val deletedUserIds =
                    usersLocalDataSource
                        .getDeletedUsers()
                        .getOrElse { emptyList() }
                        .toSet()

                users.results
                    .map { it.toDomain() }
                    .distinct()
                    .filterNot { it.uuid in deletedUserIds }
            }

        override suspend fun deleteUser(uuid: String): Either<UsersErrors, Unit> = usersLocalDataSource.deleteUser(uuid)

        override suspend fun getSeed(): Either<UsersErrors, String?> = seedLocalDataSource.getSeed()

        override suspend fun saveSeed(seed: String): Either<UsersErrors, Unit> = seedLocalDataSource.saveSeed(seed)
    }
