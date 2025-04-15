package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.database.dao.UserDao
import com.random.users.database.model.DeletedUserEntity
import com.random.users.domain.models.UserErrors
import javax.inject.Inject

internal class UsersDatabaseDataSource
    @Inject
    constructor(
        private val userDao: UserDao,
    ) : UsersLocalDataSource {
        override suspend fun getDeletedUsers() =
            Either
                .catch {
                    userDao.getAllDeletedUsers()
                }.mapLeft { UserErrors.UserError }

        override suspend fun deleteUser(user: DeletedUserEntity) =
            Either
                .catch {
                    userDao.insertDeletedUser(user)
                }.mapLeft { UserErrors.UserError }
    }
