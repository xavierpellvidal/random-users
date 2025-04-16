package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.database.dao.UserDao
import com.random.users.database.model.DeletedUserEntity
import com.random.users.domain.models.UsersErrors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UsersDatabaseDataSource
    @Inject
    constructor(
        private val userDao: UserDao,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : UsersLocalDataSource {
        override suspend fun getDeletedUsers() =
            withContext(dispatcher) {
                Either
                    .catch {
                        userDao.getAllDeletedUsers()
                    }.mapLeft { UsersErrors.UserError }
            }

        override suspend fun deleteUser(user: DeletedUserEntity) =
            withContext(dispatcher) {
                Either
                    .catch {
                        userDao.insertDeletedUser(user)
                    }.mapLeft { UsersErrors.UserError }
            }
    }
