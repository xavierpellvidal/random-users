package com.random.users.data.repository

import arrow.core.Either
import com.random.users.data.datasource.SeedLocalDataSource
import com.random.users.data.datasource.UsersLocalDataSource
import com.random.users.data.datasource.UsersRemoteDataSource
import com.random.users.data.mapper.SeedMapper.toDomain
import com.random.users.data.mapper.UserMapper.toDomain
import com.random.users.domain.models.User
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
        ): Either<UsersErrors, List<User>> {
            val currentSeed = seedLocalDataSource.getSeed().getOrNull()
            return usersRemoteDataSource
                .getUsers(
                    page = page,
                    results = results,
                    seed = currentSeed,
                ).map { users ->
                    val newSeed = users.info.toDomain()
                    if (currentSeed != newSeed) {
                        seedLocalDataSource.saveSeed(newSeed)
                    }
                    users.results.toDomain()
                }
        }

        override suspend fun getDeletedUsers() = usersLocalDataSource.getDeletedUsers()

        override suspend fun deleteUser(uuid: String): Either<UsersErrors, Unit> = usersLocalDataSource.deleteUser(uuid)
    }
