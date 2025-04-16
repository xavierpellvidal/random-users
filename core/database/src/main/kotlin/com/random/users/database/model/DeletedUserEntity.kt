package com.random.users.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_users")
data class DeletedUserEntity(
    @PrimaryKey
    var uuid: String,
)
