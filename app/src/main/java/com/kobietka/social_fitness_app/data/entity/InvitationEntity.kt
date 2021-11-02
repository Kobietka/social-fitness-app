package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.kobietka.social_fitness_app.domain.model.Invitation


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

fun InvitationEntity.toInvitation(): Invitation {
    return Invitation(
        id = this.id,
        groupId = this.groupId,
        code = this.code
    )
}
