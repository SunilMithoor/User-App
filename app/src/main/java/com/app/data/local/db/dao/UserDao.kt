package com.app.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.data.local.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserData(user: UserEntity)

    @Query("SELECT * FROM userEntity")
    suspend fun getUserData(): UserEntity
}