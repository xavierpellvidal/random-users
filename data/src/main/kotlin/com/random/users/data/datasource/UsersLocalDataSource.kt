package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.database.model.DeletedUserEntity
import com.random.users.domain.models.UsersErrors

interface UsersLocalDataSource {
    suspend fun getDeletedUsers(): Either<UsersErrors, List<DeletedUserEntity>>

    suspend fun deleteUser(user: DeletedUserEntity): Either<UsersErrors, Unit>
}
