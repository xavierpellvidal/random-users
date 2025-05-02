package com.random.users.users.di

import android.content.Context
import androidx.room.Room
import com.random.users.database.RandomUsersDatabase
import com.random.users.database.dao.UserDao
import com.random.users.database.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class],
)
object TestDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ) = Room
        .inMemoryDatabaseBuilder(appContext, RandomUsersDatabase::class.java)
        .build()

    @Provides
    @Singleton
    fun provideUserDao(db: RandomUsersDatabase): UserDao = db.userDao()
}
