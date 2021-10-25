package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


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






















