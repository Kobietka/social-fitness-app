package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "invitation",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId"),
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class InvitationEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val groupId: String,
    @ColumnInfo val code: String
)
