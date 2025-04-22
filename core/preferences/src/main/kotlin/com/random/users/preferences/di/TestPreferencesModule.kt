package com.random.users.preferences.di

import android.content.Context
import android.content.SharedPreferences
import com.random.users.preferences.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PreferencesModule::class],
)
object TestPreferencesModule {
    private const val TEST_PREFERENCES_NAME = "test_preferences"

    @Provides
    @Singleton
    fun provideFakeSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences(TEST_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager =
        PreferencesManager(sharedPreferences)
}
