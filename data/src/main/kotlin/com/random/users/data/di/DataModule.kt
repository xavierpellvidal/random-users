package com.random.users.data.di

import com.random.users.api.api.UsersApi
import com.random.users.data.datasource.SeedLocalDataSource
import com.random.users.data.datasource.SeedPreferencesDataSource
import com.random.users.data.datasource.UsersApiDataSource
import com.random.users.data.datasource.UsersDatabaseDataSource
import com.random.users.data.datasource.UsersLocalDataSource
import com.random.users.data.datasource.UsersRemoteDataSource
import com.random.users.data.repository.UsersRepositoryImpl
import com.random.users.database.dao.UserDao
import com.random.users.domain.repository.UsersRepository
import com.random.users.preferences.manager.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideUsersRemoteDataSource(
        usersApi: UsersApi,
        dispatcher: CoroutineDispatcher,
    ): UsersRemoteDataSource = UsersApiDataSource(usersApi, dispatcher)

    @Provides
    @Singleton
    fun provideUsersLocalDataSource(
        userDao: UserDao,
        dispatcher: CoroutineDispatcher,
    ): UsersLocalDataSource = UsersDatabaseDataSource(userDao, dispatcher)

    @Provides
    @Singleton
    fun provideSeedLocalDataSource(
        preferencesManager: PreferencesManager,
        dispatcher: CoroutineDispatcher,
    ): SeedLocalDataSource = SeedPreferencesDataSource(preferencesManager, dispatcher)

    @Provides
    fun provideUsersRepository(
        usersLocalDataSource: UsersLocalDataSource,
        usersRemoteDataSource: UsersRemoteDataSource,
        seedLocalDataSource: SeedLocalDataSource,
    ): UsersRepository =
        UsersRepositoryImpl(
            usersLocalDataSource = usersLocalDataSource,
            usersRemoteDataSource = usersRemoteDataSource,
            seedLocalDataSource = seedLocalDataSource,
        )
}
