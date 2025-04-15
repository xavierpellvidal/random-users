package com.random.users.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedUserEntity(
    @PrimaryKey
    var uuid: String,
)
