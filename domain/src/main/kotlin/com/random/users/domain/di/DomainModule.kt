package com.random.users.domain.di

import com.random.users.domain.repository.UsersRepository
import com.random.users.domain.usecase.DeleteUserUseCase
import com.random.users.domain.usecase.GetUserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetUserListUseCase(usersRepository: UsersRepository): GetUserListUseCase =
        GetUserListUseCase(usersRepository)

    @Provides
    fun provideDeleteUserUseCase(usersRepository: UsersRepository): DeleteUserUseCase =
        DeleteUserUseCase(usersRepository)
}
