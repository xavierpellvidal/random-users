package com.random.users.database.di

import android.content.Context
import androidx.room.Room
import com.random.users.database.RandomUsersDatabase
import com.random.users.database.RandomUsersDatabase.Companion.DB_NAME
import com.random.users.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ) = Room
        .databaseBuilder(
            appContext,
            RandomUsersDatabase::class.java,
            DB_NAME,
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(db: RandomUsersDatabase): UserDao = db.userDao()
}
