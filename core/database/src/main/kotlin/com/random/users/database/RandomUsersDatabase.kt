package com.random.users.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.random.users.database.dao.UserDao
import com.random.users.database.model.DeletedUserEntity

@Database(
    entities = [DeletedUserEntity::class],
    version = RandomUsersDatabase.Companion.DB_VERSION,
    exportSchema = false,
)
abstract class RandomUsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "random_users_database"
        const val DELETED_USERS_TABLE = "deleted_users"
        const val DB_VERSION = 1
    }
}
