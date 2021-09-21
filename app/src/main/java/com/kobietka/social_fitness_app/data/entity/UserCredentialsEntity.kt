package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userCredentials")
data class UserCredentialsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val token: String,
    @ColumnInfo val refreshToken: String
)
