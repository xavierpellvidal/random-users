package com.random.users.domain.usecase

import arrow.core.Either
import arrow.core.getOrElse
import com.random.users.domain.models.User
import com.random.users.domain.models.UsersErrors
import com.random.users.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserListUseCase
    @Inject
    constructor(
        private val usersRepository: UsersRepository,
    ) {
        suspend operator fun invoke(
            page: Int,
            results: Int = DEFAULT_RESULTS,
        ): Either<UsersErrors, List<User>> =
            usersRepository
                .getUsers(
                    page = page,
                    results = results,
                ).map { users ->
                    val deletedUsers =
                        usersRepository
                            .getDeletedUsers()
                            .getOrElse { emptyList() }
                            .toSet()
                    users
                        .distinctBy { it.uuid }
                        .filterNot { it.uuid in deletedUsers }
                }

        companion object {
            const val DEFAULT_RESULTS = 10
        }
    }
