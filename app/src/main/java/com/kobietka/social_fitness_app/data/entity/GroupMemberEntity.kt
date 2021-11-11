package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.kobietka.social_fitness_app.domain.model.GroupMember


@Entity(
    tableName = "groupMember",
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
    @PrimaryKey val id: String,
    @ColumnInfo val userId: String,
    @ColumnInfo val groupId: String,
    @ColumnInfo val nickname: String,
    @ColumnInfo val joinDate: String
)

fun GroupMemberEntity.toGroupMember(): GroupMember{
    return GroupMember(
        id = this.id,
        userId = this.userId,
        nickname = this.nickname
    )
}
