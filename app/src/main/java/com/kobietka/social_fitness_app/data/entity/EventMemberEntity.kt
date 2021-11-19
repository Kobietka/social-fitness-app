package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.kobietka.social_fitness_app.domain.model.EventMember


@Entity(
    tableName = "eventMember",
    primaryKeys = ["userId", "eventId"],
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class EventMemberEntity(
    @ColumnInfo val userId: String,
    @ColumnInfo val eventId: String,
    @ColumnInfo val nickname: String,
    @ColumnInfo val totalScore: Int
)

fun EventMemberEntity.toEventMember(): EventMember {
    return EventMember(
        userId = this.userId,
        nickname = this.nickname,
        totalScore = this.totalScore
    )
}
