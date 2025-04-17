package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.domain.models.UsersErrors
import com.random.users.preferences.manager.PreferencesManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SeedPreferencesDataSource
    @Inject
    constructor(
        private val preferencesManager: PreferencesManager,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : SeedLocalDataSource {
        override suspend fun getSeed() =
            withContext(dispatcher) {
                Either
                    .catch {
                        preferencesManager.getString(SEED_KEY)
                    }.mapLeft { UsersErrors.SeedError }
            }

        override suspend fun saveSeed(seed: String) =
            withContext(dispatcher) {
                Either
                    .catch {
                        preferencesManager.saveString(SEED_KEY, seed)
                    }.mapLeft { UsersErrors.SeedError }
            }

        companion object {
            private const val SEED_KEY = "seed_key"
        }
    }
