package com.random.users.preferences.di

import android.content.Context
import android.content.SharedPreferences
import com.random.users.preferences.manager.PreferencesManager.Companion.PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferencesManager(
        sharedPreferences: SharedPreferences,
    ): com.random.users.preferences.manager.PreferencesManager =
        com.random.users.preferences.manager
            .PreferencesManager(sharedPreferences)
}
