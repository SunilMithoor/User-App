package com.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.data.local.db.dao.UserDao
import com.app.data.local.db.entity.UserEntity


/**
 * Created by Sunil
 */

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}