package com.app.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val userId: String,
    val email: String,
    val password: String
)