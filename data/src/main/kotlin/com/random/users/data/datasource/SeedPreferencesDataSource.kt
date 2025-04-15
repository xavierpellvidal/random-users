package com.random.users.data.datasource

import arrow.core.Either
import com.random.users.domain.models.UserErrors
import com.random.users.preferences.manager.PreferencesManager
import javax.inject.Inject

internal class SeedPreferencesDataSource
    @Inject
    constructor(
        private val preferencesManager: PreferencesManager,
    ) : SeedLocalDataSource {
        override fun getSeed() =
            Either
                .catch {
                    preferencesManager.getString(SEED_KEY)
                }.mapLeft { UserErrors.SeedError }

        override fun saveSeed(seed: String) = preferencesManager.saveString(SEED_KEY, seed)

        companion object {
            private const val SEED_KEY = "seed_key"
        }
    }
