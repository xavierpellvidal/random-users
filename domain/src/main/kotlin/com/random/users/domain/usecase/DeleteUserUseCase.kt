package com.random.users.domain.usecase

import com.random.users.domain.repository.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase
    @Inject
    constructor(
        private val usersRepository: UsersRepository,
    ) {
        suspend operator fun invoke(uuid: String) = usersRepository.deleteUser(uuid)
    }
