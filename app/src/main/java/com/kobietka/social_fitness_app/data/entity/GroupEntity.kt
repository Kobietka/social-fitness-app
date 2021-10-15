package com.kobietka.social_fitness_app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "group")
data class GroupEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val ownerId: String,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val invitationCode: String? = null
)
