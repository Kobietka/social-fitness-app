package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kobietka.social_fitness_app.domain.model.User

@Entity(tableName = "user")
data class UserCredentialsEntity(
    @PrimaryKey val id: String = "",
    @ColumnInfo val token: String,
    @ColumnInfo val nickname: String
)

fun UserCredentialsEntity.toUser(): User {
    return User(
        id = this.id,
        nickname = this.nickname
    )
}