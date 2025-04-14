package com.random.users.data.di

import com.random.users.api.service.UsersApi
import com.random.users.data.datasource.UserPreferencesDataSource
import com.random.users.data.datasource.UsersApiDataSource
import com.random.users.data.datasource.UsersLocalDataSource
import com.random.users.data.datasource.UsersRemoteDataSource
import com.random.users.preferences.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideUsersRemoteDataSource(usersApi: UsersApi): UsersRemoteDataSource = UsersApiDataSource(usersApi)

    @Provides
    @Singleton
    fun provideUsersLocalDataSource(preferencesManager: PreferencesManager): UsersLocalDataSource =
        UserPreferencesDataSource(preferencesManager)
}
