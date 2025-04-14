package com.random.users.data.di

import com.random.users.api.service.UsersApi
import com.random.users.data.datasource.UsersApiDataSource
import com.random.users.data.datasource.UsersRemoteDataSource
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
}
