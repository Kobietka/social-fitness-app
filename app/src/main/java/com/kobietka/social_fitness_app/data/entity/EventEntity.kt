package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.kobietka.social_fitness_app.domain.model.Event
import com.kobietka.social_fitness_app.domain.model.EventType


@Entity(
    tableName = "event",
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
data class EventEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val groupId: String,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val pointGoal: Int,
    @ColumnInfo val pointPerMinute: Int,
    @ColumnInfo val pointPerRep: Int,
    @ColumnInfo val startDate: String,
    @ColumnInfo val endDate: String,
    @ColumnInfo val eventType: String
)

fun EventEntity.toEvent(): Event {
    return Event(
        id = this.id,
        groupId = this.groupId,
        name = this.name,
        description = this.description,
        pointGoal = this.pointGoal,
        pointPerMinute = this.pointPerMinute,
        pointPerRep = this.pointPerRep,
        startDate = this.startDate,
        endDate = this.endDate,
        eventType = when(this.eventType){
            "TIME" -> EventType.TIME
            "REP" -> EventType.REPETITION
            "LESS_TIME" -> EventType.LESS_TIME
            else -> EventType.UNKNOWN
        }
    )
}





































