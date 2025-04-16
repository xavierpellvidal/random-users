package com.random.users.preferences.manager

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PreferencesManager
    @Inject
    constructor(
        private val sharedPreferences: SharedPreferences,
    ) {
        fun saveString(
            key: String,
            value: String,
        ) = sharedPreferences.edit { putString(key, value) }

        fun getString(
            key: String,
            defaultValue: String? = null,
        ): String? = sharedPreferences.getString(key, defaultValue)

        companion object {
            const val PREFERENCES_NAME = "user_preferences"
        }
    }
