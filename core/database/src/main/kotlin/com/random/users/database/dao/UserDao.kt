package com.random.users.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.random.users.database.model.DeletedUserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeletedUser(user: DeletedUserEntity)

    @Query("SELECT * FROM deleteduserentity")
    suspend fun getAllDeletedUsers(): List<DeletedUserEntity>
}
