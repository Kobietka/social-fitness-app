package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE


@Entity(
    tableName = "groupMember",
    primaryKeys = ["userId", "groupId"],
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class GroupMemberEntity(
    @ColumnInfo val userId: String,
    @ColumnInfo val groupId: String,
    @ColumnInfo val nickname: String,
    @ColumnInfo val joinDate: String
)
