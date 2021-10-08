package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserCredentialsEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo val token: String,
    @ColumnInfo val nickname: String
)
