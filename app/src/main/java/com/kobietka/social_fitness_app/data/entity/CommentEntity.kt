package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "comment",
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("postId"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class CommentEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val postId: String,
    @ColumnInfo val userId: String,
    @ColumnInfo val content: String,
    @ColumnInfo val createdAt: String
)



















