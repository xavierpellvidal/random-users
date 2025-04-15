package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.database.model.DeletedUserEntity
import com.random.users.domain.models.UserErrors

interface UsersLocalDataSource {
    suspend fun getDeletedUsers(): Either<UserErrors, List<DeletedUserEntity>>

    suspend fun deleteUser(user: DeletedUserEntity): Either<UserErrors, Unit>
}
