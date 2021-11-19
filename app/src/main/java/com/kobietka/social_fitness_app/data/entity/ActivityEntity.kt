package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.kobietka.social_fitness_app.domain.model.Activity


@Entity(
    tableName = "activity",
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("eventId"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class ActivityEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val userId: String,
    @ColumnInfo val eventId: String,
    @ColumnInfo val name: String,
    @ColumnInfo val value: Int,
    @ColumnInfo val createdAt: String
)

fun ActivityEntity.toActivity(): Activity {
    return Activity(
        id = this.id,
        userId = this.userId,
        name = this.name,
        value = this.value,
        createdAt = this.createdAt
    )
}
