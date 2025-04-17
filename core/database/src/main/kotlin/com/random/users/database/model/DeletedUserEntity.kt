package com.random.users.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.random.users.database.RandomUsersDatabase.Companion.DELETED_USERS_TABLE

@Entity(tableName = DELETED_USERS_TABLE)
data class DeletedUserEntity(
    @PrimaryKey
    var uuid: String,
)
