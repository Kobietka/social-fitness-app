package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "post",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class PostEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val groupId: String,
    @ColumnInfo val content: String,
    @ColumnInfo val userId: String,
    @ColumnInfo val createdAt: String
)
